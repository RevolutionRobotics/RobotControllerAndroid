package com.revolution.robotics.features.coding.programs

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.features.coding.programs.adapter.ProgramViewModel

class ProgramsPresenter(private val getUserProgramsInteractor: GetUserProgramsInteractor) : ProgramsMvp.Presenter {

    override var view: ProgramsMvp.View? = null
    override var model: ProgramsViewModel? = null

    override fun loadPrograms() {
        getUserProgramsInteractor.execute(
            onResponse = { programs ->
                view?.onProgramsLoaded(programs.map { ProgramViewModel(it, this) })
            },
            onError = {
                // TODO add error handling
            }
        )
    }

    override fun onProgramSelected(program: UserProgram) {
        view?.onProgramSelected(program)
    }
}
