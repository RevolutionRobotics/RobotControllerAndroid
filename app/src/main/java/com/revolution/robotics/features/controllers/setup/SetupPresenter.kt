package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentItem
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel

class SetupPresenter(
    private val getProgramsInteractor: GetUserProgramsInteractor,
    private val compatibleProgramFilterer: CompatibleProgramFilterer,
    private val storage: UserConfigurationStorage
) : SetupMvp.Presenter {

    companion object {
        private const val MOST_RECENT_PROGRAM_COUNT = 5
    }

    override var view: SetupMvp.View? = null
    override var model: SetupViewModel? = null

    private val programs = ArrayList<UserProgram>()

    override fun register(view: SetupMvp.View, model: SetupViewModel?) {
        super.register(view, model)
        loadUserPrograms()
    }

    private fun loadUserPrograms() {
        getProgramsInteractor.execute(
            onResponse = { result ->
                programs.clear()
                programs.addAll(compatibleProgramFilterer.getCompatibleProgramsOnly(result))
            },
            onError = {
                // TODO error handling
            })
    }

    override fun onProgramSlotSelected(index: Int) {
        val availablePrograms = programs.toMutableList()
        storage.getButtonPrograms().forEach { boundProgram ->
            availablePrograms.removeAll { it.id == boundProgram.programId }
        }

        var mostRecentPrograms = availablePrograms.sortedBy { it.lastModified }.reversed()
        if (mostRecentPrograms.size > MOST_RECENT_PROGRAM_COUNT) {
            mostRecentPrograms = mostRecentPrograms.subList(0, MOST_RECENT_PROGRAM_COUNT)
        }
        val hasMorePrograms = availablePrograms.size > mostRecentPrograms.size
        val mostRecentItems = mostRecentPrograms.map { MostRecentItem(it) }.toMutableList()
        model?.getProgram(index)?.let { boundProgram -> mostRecentItems.add(0, MostRecentItem(boundProgram, true)) }

        val mostRecentViewModel = MostRecentProgramViewModel(mostRecentItems, hasMorePrograms, this)
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

    override fun onControllerSetupFinished() {
        // TODO
    }
}
