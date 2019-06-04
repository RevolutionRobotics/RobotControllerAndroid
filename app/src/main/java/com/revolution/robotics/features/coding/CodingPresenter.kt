package com.revolution.robotics.features.coding

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.RemoveUserProgramInteractor
import com.revolution.robotics.core.interactor.SaveUserProgramInteractor
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.utils.UserProgramFileNameGenerator
import com.revolution.robotics.features.coding.programs.ProgramsDialog
import com.revolution.robotics.features.coding.saveProgram.SaveProgramDialog
import java.io.File

class CodingPresenter(
    private val removeUserProgramInteractor: RemoveUserProgramInteractor,
    private val saveUserProgramInteractor: SaveUserProgramInteractor,
    private val fileNameGenerator: UserProgramFileNameGenerator,
    private val applicationContextProvider: ApplicationContextProvider
) : CodingMvp.Presenter {

    override var view: CodingMvp.View? = null
    override var model: CodingViewModel? = null

    private var userProgram: UserProgram? = null
    private var pythonSaved = false
    private var xmlSaved = false
    private var variablesSaved = false

    override fun showProgramsDialog() {
        view?.showDialog(ProgramsDialog.newInstance())
    }

    override fun showSaveProgramDialog(userProgram: UserProgram?) {
        view?.showDialog(SaveProgramDialog.newInstance(userProgram))
    }

    override fun setSavedProgramData(userProgram: UserProgram) {
        this.userProgram = userProgram
    }

    override fun removeProgram(userProgram: UserProgram) {
        removeUserProgramInteractor.userProgramId = userProgram.id
        removeUserProgramInteractor.execute()
    }

    override fun onPythonProgramSaved(file: String) {
        "${applicationContextProvider.applicationContext.filesDir}/${fileNameGenerator.generatePythonFileName()}".also { fileName ->
            File(fileName).writeText(file)
            userProgram?.python = fileName
            pythonSaved = true
            saveUserProgramWhenEveryDataIsReady()
        }
    }

    override fun onXMLProgramSaved(file: String) {
        "${applicationContextProvider.applicationContext.filesDir}/${fileNameGenerator.generateXmlFileName()}".also { fileName ->
            File(fileName).writeText(file)
            userProgram?.xml = fileName
            xmlSaved = true
            saveUserProgramWhenEveryDataIsReady()
        }
    }

    override fun onVariablesExported(variables: String) {
        userProgram?.variables = variables.split(",")
        variablesSaved = true
        saveUserProgramWhenEveryDataIsReady()
    }

    private fun saveUserProgramWhenEveryDataIsReady() {
        if (pythonSaved && xmlSaved && variablesSaved) {
            userProgram?.let { userProgram ->
                saveUserProgramInteractor.userProgram = userProgram
                saveUserProgramInteractor.execute {
                    xmlSaved = false
                    pythonSaved = false
                    variablesSaved = false
                }
            }
        }
    }
}
