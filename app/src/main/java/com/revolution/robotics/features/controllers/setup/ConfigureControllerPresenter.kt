package com.revolution.robotics.features.controllers.setup

import android.os.Bundle
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.*
import com.revolution.robotics.features.configure.ConfigurationEventBus
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.configure.controller.ControllerButton
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentItem
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel

class ConfigureControllerPresenter(
    private val getUserRobotInteractor: GetUserRobotInteractor,
    private val getFullConfigurationInteractor: GetFullConfigurationInteractor,
    private val compatibleProgramFilterer: CompatibleProgramFilterer,
    private val getUserProgramsForRobotInteractor: GetUserProgramsForRobotInteractor,
    private val assignProgramToButtonInteractor: AssignProgramToButtonInteractor,
    private val configurationEventBus: ConfigurationEventBus,
    private val reporter: Reporter

    ) : ConfigureControllerMvp.Presenter {

    companion object {
        private const val MOST_RECENT_PROGRAM_COUNT = 5
        private const val INDEX_NOT_FOUND = -1
    }

    private val buttonNames = ControllerButton.values().toList()

    override var view: ConfigureControllerMvp.View? = null
    override var model: ConfigureControllerViewModel? = null

    private var robotId: Int = -1
    private val allPrograms = ArrayList<UserProgram>()


    override fun loadControllerAndPrograms(robotId: Int) {
        this.robotId = robotId
        getFullConfigurationInteractor.robotId = robotId
        getFullConfigurationInteractor.execute { fullControllerData ->
            fullControllerData.controller?.let { model?.update(it) }
            view?.updateContentBindings()
            loadPrograms()
        }
    }

    private fun loadPrograms() {
        getUserRobotInteractor.robotId = robotId
        getUserRobotInteractor.execute { robot->
            robot?.id?.let {
                getUserProgramsForRobotInteractor.robotId = it
                getUserProgramsForRobotInteractor.execute { result ->
                    allPrograms.clear()
                    allPrograms.addAll(result)
                }
            }
        }
    }

    override fun onProgramSlotSelected(index: Int) {
        getFullConfigurationInteractor.robotId = robotId
        getFullConfigurationInteractor.execute { fullConfig ->
            fullConfig.controller?.let { model?.update(it) }
            view?.updateContentBindings()
            var programs =
                compatibleProgramFilterer.getCompatibleProgramsOnly(allPrograms, fullConfig.userConfiguration)
            val mostRecentViewModel =
                if (index == ConfigureControllerViewModel.NO_PROGRAM_SELECTED) {
                    null
                } else {
                    val availablePrograms = programs.toMutableList()
                    fullConfig.controller?.userController?.getBoundButtonPrograms()?.forEach { boundProgram ->
                        availablePrograms.removeAll { it.name == boundProgram.programName }
                    }
                    fullConfig.controller?.backgroundBindings?.forEach { backgroundBinding ->
                        availablePrograms.removeAll { it.name == backgroundBinding.programName }
                    }

                    var mostRecentPrograms = availablePrograms.sortedBy { it.lastModified }.reversed()
                    if (mostRecentPrograms.size > MOST_RECENT_PROGRAM_COUNT) {
                        mostRecentPrograms = mostRecentPrograms.subList(0, MOST_RECENT_PROGRAM_COUNT)
                    }
                    val mostRecentItems = mostRecentPrograms.map { MostRecentItem(it) }.toMutableList()
                    model?.getProgram(index)?.let { boundProgram ->
                        mostRecentItems.add(0, MostRecentItem(boundProgram, true))
                    }

                    MostRecentProgramViewModel(mostRecentItems, this)
                }
            view?.onProgramSlotSelected(index, mostRecentViewModel)
        }
    }


    override fun showProgramDialog(program: UserProgram, isBound: Boolean) {
        if (isBound) {
            view?.showDialog(ProgramDialog.Remove.newInstance(program))
        } else {
            view?.showDialog(ProgramDialog.Add.newInstance(program))
        }
    }

    override fun onProgramSelected(program: UserProgram, isBound: Boolean) {
        if (isBound) {
            removeProgram()
        } else {
            addProgram(program)
        }
    }

    override fun addProgram(userProgram: UserProgram) {
        model?.selectedProgram?.let {
            assignProgramToButtonInteractor.robotId = robotId
            assignProgramToButtonInteractor.button = buttonNames[model?.selectedProgram!! - 1]
            assignProgramToButtonInteractor.userProgram = userProgram
            assignProgramToButtonInteractor.execute {
                model?.onProgramSet(userProgram)
                view?.hideProgramSelector()
                reporter.reportEvent(Reporter.Event.ASSIGN_PROGRAM_TO_BUTTON, Bundle().apply {
                    putBoolean(Reporter.Parameter.CUSTOM.parameterName, userProgram.remoteId == null)
                })
            }
        }
    }

    override fun removeProgram() {
        model?.selectedProgram?.let {
            assignProgramToButtonInteractor.robotId = robotId
            assignProgramToButtonInteractor.button = buttonNames[model?.selectedProgram!! - 1]
            assignProgramToButtonInteractor.userProgram = null
            assignProgramToButtonInteractor.execute {
                model?.onProgramSet(null)
                view?.hideProgramSelector()
            }
        }
    }

    override fun showAllPrograms() {
        model?.selectedProgram?.let {
            view?.showAllPrograms(buttonNames[it - 1], robotId)
        }
    }

    override fun onProgramEdited(program: UserProgram) {
        getFullConfigurationInteractor.robotId = robotId
        getFullConfigurationInteractor.execute { result ->
            if (!compatibleProgramFilterer.isProgramCompatible(program, result.userConfiguration)) {
                val index = model?.getProgramIndex(program)
                if (index != null && index != INDEX_NOT_FOUND) {
                    model?.selectedProgram = index + 1
                    removeProgram()
                }
            }
        }
    }

    override fun play() {
        configurationEventBus.publishPlayEvent()
    }
}
