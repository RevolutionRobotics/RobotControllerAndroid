package com.revolution.robotics.features.coding

import android.util.Base64
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.RemoveUserProgramInteractor
import com.revolution.robotics.core.interactor.SaveUserProgramInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.coding.programs.ProgramsDialog
import com.revolution.robotics.features.coding.python.PythonDialog
import com.revolution.robotics.features.coding.saveProgram.SaveProgramDialog
import org.revolution.blockly.view.jsInterface.SaveBlocklyListener
import java.util.concurrent.TimeUnit

@Suppress("TooManyFunctions")
class CodingPresenter(
    private val removeUserProgramInteractor: RemoveUserProgramInteractor,
    private val saveUserProgramInteractor: SaveUserProgramInteractor,
    private val resourceResolver: ResourceResolver
) : CodingMvp.Presenter {

    override var view: CodingMvp.View? = null
    override var model: CodingViewModel? = null

    private var userProgram: UserProgram? = null
    private var pythonSaved = false
    private var xmlSaved = false
    private var variablesSaved = false

    override fun loadProgram(userProgram: UserProgram) {
        model?.userProgram = userProgram
        model?.programName?.set(userProgram.name)
        userProgram.xml?.let { xmlFile ->
            view?.loadProgramIntoTheBlockly(String(Base64.decode(xmlFile, Base64.NO_WRAP)))
        }
    }

    override fun showProgramsDialog() {
        view?.showDialog(ProgramsDialog.newInstance())
    }

    override fun showSaveProgramDialog(userProgram: UserProgram?) {
        view?.showDialog(SaveProgramDialog.newInstance(userProgram))
    }

    override fun setSavedProgramData(userProgram: UserProgram) {
        this.userProgram = userProgram
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
        removeUserProgramInteractor.execute()
    }

    override fun onPythonProgramSaved(file: String) {
        userProgram?.python = String(Base64.encode(file.toByteArray(), Base64.NO_WRAP))
        pythonSaved = true
        saveUserProgramWhenEveryDataIsReady()
    }

    override fun onXMLProgramSaved(file: String) {
        userProgram?.xml = String(Base64.encode(file.toByteArray(), Base64.NO_WRAP))
        xmlSaved = true
        saveUserProgramWhenEveryDataIsReady()
    }

    override fun showPythonCode() {
        view?.getPythonCodeFromBlockly(object : SaveBlocklyListener {
            override fun onPythonProgramSaved(file: String) {
                view?.showDialog(PythonDialog.newInstance(file))
            }

            override fun onXMLProgramSaved(file: String) = Unit

            override fun onVariablesExported(variables: String) = Unit
        })
    }

    override fun onVariablesExported(variables: String) {
        userProgram?.variables =
            if (variables.isEmpty()) {
                emptyList()
            } else {
                variables.split(",")
            }
        variablesSaved = true
        saveUserProgramWhenEveryDataIsReady()
    }

    private fun saveUserProgramWhenEveryDataIsReady() {
        if (pythonSaved && xmlSaved && variablesSaved) {
            userProgram?.let { userProgram ->
                userProgram.lastModified = System.currentTimeMillis() / TimeUnit.SECONDS.toMillis(1)
                saveUserProgramInteractor.userProgram = userProgram
                saveUserProgramInteractor.clearRemoteId = true
                saveUserProgramInteractor.execute {
                    xmlSaved = false
                    pythonSaved = false
                    variablesSaved = false
                    view?.onProgramSaved()
                }
            }
        }
    }

    override fun onBackPressed() {
        view?.onToolbarBackPressed()
    }
}
