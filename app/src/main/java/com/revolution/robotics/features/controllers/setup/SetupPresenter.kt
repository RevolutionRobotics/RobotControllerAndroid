package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.features.controllers.programInfo.ProgramInfoDialog
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentItem
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel

class SetupPresenter(private val getProgramsInteractor: GetUserProgramsInteractor) : SetupMvp.Presenter {

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
        model?.getProgram(index)?.let { boundProgram ->
            mostRecent.removeAll { it.program == boundProgram }
            mostRecent.add(0, MostRecentItem(boundProgram, true))
        }
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
