package com.revolution.robotics.features.coding

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.LocalFileLoader
import com.revolution.robotics.core.interactor.LocalFileSaver
import com.revolution.robotics.core.interactor.RemoveUserProgramInteractor
import com.revolution.robotics.core.interactor.SaveUserProgramInteractor
import com.revolution.robotics.core.utils.UserProgramFileNameGenerator
import com.revolution.robotics.features.coding.programs.ProgramsDialog
import com.revolution.robotics.features.coding.saveProgram.SaveProgramDialog

class CodingPresenter(
    private val removeUserProgramInteractor: RemoveUserProgramInteractor,
    private val saveUserProgramInteractor: SaveUserProgramInteractor,
    private val fileNameGenerator: UserProgramFileNameGenerator,
    private val localFileLoader: LocalFileLoader,
    private val localFileSaver: LocalFileSaver
) : CodingMvp.Presenter {
    override fun loadProgram(userProgram: UserProgram) {
        model?.userProgram = userProgram
        model?.programName?.set(userProgram.name)
        userProgram.xml?.let { xmlFile ->
            localFileLoader.filePath = xmlFile
            localFileLoader.execute { xml ->
                view?.loadProgramIntoTheBlockly(xml)
            }
        }
    }

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
        model?.programName?.set(userProgram.name)
    }

    override fun removeProgram(userProgram: UserProgram) {
        model?.userProgram = null
        model?.programName?.set("")
        removeUserProgramInteractor.userProgramId = userProgram.id
        removeUserProgramInteractor.execute()
    }

    override fun onPythonProgramSaved(file: String) {
        fileNameGenerator.generatePythonFileName(true).also { fileName ->
            localFileSaver.filePath = fileName
            localFileSaver.content = file
            localFileSaver.execute {
                userProgram?.python = fileName
                pythonSaved = true
                saveUserProgramWhenEveryDataIsReady()
            }
        }
    }

    override fun onXMLProgramSaved(file: String) {
        fileNameGenerator.generateXmlFileName(true).also { fileName ->
            localFileSaver.filePath = fileName
            localFileSaver.content = file
            localFileSaver.execute {
                userProgram?.xml = fileName
                xmlSaved = true
                saveUserProgramWhenEveryDataIsReady()
            }
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
                saveUserProgramInteractor.clearRemoteId = true
                saveUserProgramInteractor.execute {
                    xmlSaved = false
                    pythonSaved = false
                    variablesSaved = false
                }
            }
        }
    }
}
