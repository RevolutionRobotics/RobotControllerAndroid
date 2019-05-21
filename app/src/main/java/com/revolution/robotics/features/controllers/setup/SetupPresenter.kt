package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.controllers.programInfo.ProgramInfoDialog
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentItem
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel

class SetupPresenter(
    private val getProgramsInteractor: GetUserProgramsInteractor,
    private val storage: UserConfigurationStorage
) : SetupMvp.Presenter {

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
                programs.addAll(result)
            },
            onError = {
                // TODO error handling
            })
    }

    override fun onProgramSlotSelected(index: Int) {
        val mostRecent = programs
            .sortedBy { it.lastModified }
            .reversed()
            .map { MostRecentItem(it) }
            .toMutableList()
        storage.getButtonPrograms().forEach { boundProgram ->
            mostRecent.removeAll { it.program.id == boundProgram.programId }
        }
        model?.getProgram(index)?.let { boundProgram -> mostRecent.add(0, MostRecentItem(boundProgram, true)) }
        // TODO what happens if less than 5 programs remain?
        // TODO what happens if 0 most recent programs remain?
        view?.onProgramSlotSelected(index, MostRecentProgramViewModel(mostRecent, this))
    }

    override fun addProgram(program: UserProgram) {
        view?.showDialog(ProgramInfoDialog.Add.newInstance(program))
    }

    override fun removeProgram(program: UserProgram) {
        view?.showDialog(ProgramInfoDialog.Remove.newInstance(program))
    }

    override fun showAllPrograms() {
        view?.onShowAllProgramsSelected()
    }

    override fun onControllerSetupFinished() {
        // TODO
    }
}
