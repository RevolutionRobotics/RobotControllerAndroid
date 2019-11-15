package com.revolution.robotics.features.coding.programs

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetAllUserRobotsInteractor
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.features.coding.programs.adapter.ProgramViewModel

class ProgramsPresenter(
    private val getUserProgramsInteractor: GetUserProgramsInteractor,
    private val getAllUserRobotsInteractor: GetAllUserRobotsInteractor
) : ProgramsMvp.Presenter {

    override var view: ProgramsMvp.View? = null
    override var model: ProgramsViewModel? = null

    override fun loadPrograms() {
        getUserProgramsInteractor.execute { programs ->
            getAllUserRobotsInteractor.execute {robots ->
                view?.onProgramsLoaded(programs.map { ProgramViewModel(it, robots.find { robot -> robot.id == it.robotId }, this) })
            }
        }
    }

    override fun onProgramSelected(program: UserProgram) {
        view?.onProgramSelected(program)
    }
}
