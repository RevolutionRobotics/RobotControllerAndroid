package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserControllerInteractor
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentItem
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel

class ConfigureControllerPresenter(
    private val getProgramsInteractor: GetUserProgramsInteractor,
    private val compatibleProgramFilterer: CompatibleProgramFilterer,
    private val storage: UserConfigurationStorage,
    private val userConfigurationStorage: UserConfigurationStorage,
    private val userControllerInteractor: GetUserControllerInteractor
) : ConfigureControllerMvp.Presenter {

    companion object {
        private const val MOST_RECENT_PROGRAM_COUNT = 5
        private const val INDEX_NOT_FOUND = -1
    }

    override var view: ConfigureControllerMvp.View? = null
    override var model: ConfigureControllerViewModel? = null

    private val programs = ArrayList<UserProgram>()

    override fun loadControllerAndPrograms(controllerId: Int) {
        userControllerInteractor.id = controllerId
        userControllerInteractor.execute { controllerWithPrograms ->
            userConfigurationStorage.controllerHolder = controllerWithPrograms
            model?.restoreFromStorage(storage)
            view?.updateContentBindings()
            loadPrograms()
        }
    }

    private fun loadPrograms() {
        getProgramsInteractor.execute { result ->
            programs.clear()
            programs.addAll(compatibleProgramFilterer.getCompatibleProgramsOnly(result, storage.userConfiguration))
        }
    }

    override fun onProgramSlotSelected(index: Int) {
        val mostRecentViewModel =
            if (index == ConfigureControllerViewModel.NO_PROGRAM_SELECTED) {
                null
            } else {
                val availablePrograms = programs.toMutableList()
                storage.getBoundButtonPrograms().forEach { boundProgram ->
                    availablePrograms.removeAll { it.name == boundProgram.programName }
                }
                storage.controllerHolder?.backgroundBindings?.forEach { backgroundBinding ->
                    availablePrograms.removeAll { it.name == backgroundBinding.programId }
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

    override fun addProgram(program: UserProgram) {
        view?.showDialog(ProgramDialog.Add.newInstance(program))
    }

    override fun removeProgram(program: UserProgram) {
        view?.showDialog(ProgramDialog.Remove.newInstance(program))
    }

    override fun showAllPrograms() {
        view?.onShowAllProgramsSelected()
    }

    override fun onProgramEdited(program: UserProgram) {
        if (!compatibleProgramFilterer.isProgramCompatible(program, userConfigurationStorage.userConfiguration)) {
            val index = model?.getProgramIndex(program)
            if (index != null && index != INDEX_NOT_FOUND) {
                model?.selectedProgram = index + 1
                view?.removeSelectedProgram()
            }
        }
    }
}
