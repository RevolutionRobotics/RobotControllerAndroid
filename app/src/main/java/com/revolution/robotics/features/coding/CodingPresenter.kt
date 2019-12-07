package com.revolution.robotics.features.coding

import android.util.Base64
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserConfigForRobotInteractor
import com.revolution.robotics.core.interactor.RemoveUserProgramInteractor
import com.revolution.robotics.core.interactor.SaveUserProgramInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.features.bluetooth.BluetoothConnectionListener
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.coding.new.NewProgramConfirmDialog
import com.revolution.robotics.features.coding.new.robotSelector.RobotSelectorDialog
import com.revolution.robotics.features.coding.programs.ProgramsDialog
import com.revolution.robotics.features.coding.python.PythonDialog
import com.revolution.robotics.features.coding.saveProgram.SaveProgramDialog
import com.revolution.robotics.features.coding.test.TestCodeDialog
import org.revolutionrobotics.blockly.android.view.jsInterface.SaveBlocklyListener
import java.util.concurrent.TimeUnit

@Suppress("TooManyFunctions")
class CodingPresenter(
    private val getUserConfigForRobotInteractor: GetUserConfigForRobotInteractor,
    private val removeUserProgramInteractor: RemoveUserProgramInteractor,
    private val saveUserProgramInteractor: SaveUserProgramInteractor,
    private val resourceResolver: ResourceResolver,
    private val bluetoothManager: BluetoothManager,
    private val appPrefs: AppPrefs
) : CodingMvp.Presenter, BluetoothConnectionListener {

    companion object {
        private const val EMPTY_XML = "<xml xmlns=\"http://www.w3.org/1999/xhtml\"></xml>"
        private val REMOVE_ID_REGEXP = Regex("id=\"[^\"]*\"")
    }

    override var view: CodingMvp.View? = null
    override var model: CodingViewModel? = null

    private var userProgramForSave: UserProgram? = null
    private var actionIdAfterSave = -1
    private var pythonSaved = false
    private var xmlSaved = false
    private var variablesSaved = false
    private var robotId: Int? = null
    private var showTestDialogAfterConnection = false

    override fun register(view: CodingMvp.View, model: CodingViewModel?) {
        super.register(view, model)
        bluetoothManager.registerListener(this)
    }

    override fun unregister(view: CodingMvp.View?) {
        super.unregister(view)
        bluetoothManager.unregisterListener(this)
    }

    override fun showNewProgramDialog() {
        view?.showDialog(NewProgramConfirmDialog.newInstance())
    }

    override fun loadProgram(userProgram: UserProgram) {
        model?.userProgram = userProgram
        model?.programName?.set(userProgram.name)
        robotId = userProgram.robotId
        loadConfig()
        userProgram.xml?.let { xmlFile ->
            appPrefs.lastOpenedProgramName = userProgram.name
            appPrefs.lastOpenedProgramRobotId = userProgram.robotId
            view?.loadProgramIntoTheBlockly(String(Base64.decode(xmlFile, Base64.NO_WRAP)))
        }
    }

    override fun showRobotSelectionDialog() {
        view?.showDialog(RobotSelectorDialog.newInstance())
    }

    override fun createNewProgram(robotId: Int) {
        this.robotId = robotId
        loadConfig()
        model?.userProgram = null
        model?.resetProgramName()
        view?.clearBlocklyWorkspace()
    }

    override fun showProgramsDialog() {
        isProgramChanged { changed ->
            if (changed) {
                view?.showDialog(LoadProgramConfirmDialog.newInstance())
            } else {
                view?.showDialog(ProgramsDialog.newInstance())
            }
        }
    }

    override fun showSaveProgramDialog(userProgram: UserProgram?, actionIdAfterSave: Int) {
        robotId?.let { robotId ->
            view?.showDialog(
                SaveProgramDialog.newInstance(
                    userProgram,
                    robotId,
                    actionIdAfterSave
                )
            )
        }
    }

    override fun setSavedProgramData(userProgram: UserProgram, actionId: Int) {
        this.userProgramForSave = userProgram
        actionIdAfterSave = actionId
        model?.programName?.set(userProgram.name)
        model?.userProgram = userProgram
    }

    override fun removeProgram(userProgram: UserProgram) {
        if (model?.userProgram?.name == userProgram.name) {
            view?.clearBlocklyWorkspace()
            model?.userProgram = null
            model?.programName?.set(resourceResolver.string(R.string.program_title_default))
        }
        removeUserProgramInteractor.userProgramName = userProgram.name
        removeUserProgramInteractor.robotId = userProgram.robotId
        removeUserProgramInteractor.execute()
    }

    override fun onPythonProgramSaved(file: String) {
        userProgramForSave?.python = String(Base64.encode(file.toByteArray(), Base64.NO_WRAP))
        pythonSaved = true
        saveUserProgramWhenEveryDataIsReady()
    }

    override fun onXMLProgramSaved(file: String) {
        userProgramForSave?.xml = String(Base64.encode(file.toByteArray(), Base64.NO_WRAP))
        xmlSaved = true
        saveUserProgramWhenEveryDataIsReady()
    }

    override fun showPythonCode() {
        view?.getDataFromBlocklyView(object : SaveBlocklyListener {
            override fun onPythonProgramSaved(file: String) {
                view?.showDialog(PythonDialog.newInstance(file))
            }

            override fun onXMLProgramSaved(file: String) = Unit

            override fun onVariablesExported(variables: String) = Unit
        })
    }

    override fun play() {
        if (bluetoothManager.isServiceDiscovered) {
            showTestDialog()
        } else {
            showTestDialogAfterConnection = true
            bluetoothManager.startConnectionFlow()
        }
    }

    override fun shareProgram() {
        var code : String? = null
        var xml : String? = null
        view?.getDataFromBlocklyView(object : SaveBlocklyListener {
            override fun onPythonProgramSaved(file: String) {
                code = file
                if (code != null && xml != null) {
                    view?.shareCode(code!!, xml!!)
                }
            }

            override fun onXMLProgramSaved(file: String) {
                xml = file
                if (code != null && xml != null) {
                    view?.shareCode(code!!, xml!!)
                }
            }

            override fun onVariablesExported(variables: String) = Unit
        })
    }

    private fun showTestDialog() {
        view?.getDataFromBlocklyView(object : SaveBlocklyListener {
            override fun onPythonProgramSaved(file: String) {
                robotId?.let {
                    view?.showDialog(TestCodeDialog.newInstance(file, it))
                }
            }

            override fun onXMLProgramSaved(file: String) = Unit

            override fun onVariablesExported(variables: String) = Unit
        })
    }

    override fun onVariablesExported(variables: String) {
        userProgramForSave?.variables =
            if (variables.isEmpty()) {
                emptyList()
            } else {
                variables.split(",")
            }
        variablesSaved = true
        saveUserProgramWhenEveryDataIsReady()
    }

    private fun loadConfig() {
        robotId?.let {
            getUserConfigForRobotInteractor.robotId = it
            getUserConfigForRobotInteractor.execute { config ->
                view?.onConfigLoaded(config)
            }
        }
    }

    private fun saveUserProgramWhenEveryDataIsReady() {
        if (pythonSaved && xmlSaved && variablesSaved) {
            userProgramForSave?.let { userProgram ->
                userProgram.lastModified = System.currentTimeMillis() / TimeUnit.SECONDS.toMillis(1)
                saveUserProgramInteractor.userProgram = userProgram
                saveUserProgramInteractor.clearRemoteId = true
                saveUserProgramInteractor.execute {
                    xmlSaved = false
                    pythonSaved = false
                    variablesSaved = false
                    handleSaveAction(actionIdAfterSave)
                    actionIdAfterSave = -1
                    appPrefs.lastOpenedProgramName = userProgram.name
                    appPrefs.lastOpenedProgramRobotId = userProgram.robotId
                    view?.onProgramSaved()
                }
            }
        }
    }

    private fun handleSaveAction(actionId: Int) {
        when (actionId) {
            CodingFragment.ACTION_ID_LEAVE -> view?.onBackPressed(false)
            CodingFragment.ACTION_ID_LOAD_PROGRAMS -> view?.showDialog(ProgramsDialog.newInstance())
            CodingFragment.ACTION_ID_CREATE_NEW_PROGRAM -> showRobotSelectionDialog()
        }
    }

    private fun isProgramChanged(callback: (changed: Boolean) -> Unit) {
        view?.getDataFromBlocklyView(object : SaveBlocklyListener {
            override fun onXMLProgramSaved(file: String) {
                callback.invoke(
                    file.removeIds() != String(
                        Base64.decode(
                            model?.userProgram?.xml ?: "",
                            Base64.NO_WRAP
                        )
                    ).removeIds() && file != EMPTY_XML
                )
            }

            override fun onPythonProgramSaved(file: String) = Unit

            override fun onVariablesExported(variables: String) = Unit
        })
    }

    private fun String.removeIds(): String = this.replace(REMOVE_ID_REGEXP, "")

    override fun onBackPressed() {
        isProgramChanged { view?.onBackPressed(it) }
    }

    override fun onBluetoothConnectionStateChanged(connected: Boolean, serviceDiscovered: Boolean) {
        if (showTestDialogAfterConnection && connected && serviceDiscovered) {
            showTestDialog()
        }
    }
}
