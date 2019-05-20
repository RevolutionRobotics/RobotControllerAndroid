package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.core.domain.local.UserProgram

class SetupPresenter : SetupMvp.Presenter {

    override var view: SetupMvp.View? = null
    override var model: SetupViewModel? = null

    override fun onProgramSlotSelected(index: Int) {
        view?.onProgramSlotSelected(
            index, MostRecentProgramViewModel(
                listOf(
                    UserProgram(name = "program 1"),
                    UserProgram(name = "program 2"),
                    UserProgram(name = "program 3"),
                    UserProgram(name = "program 4"),
                    UserProgram(name = "program 5")
                ),
                this
            )
        )
    }

    override fun showAllPrograms() {
        view?.onShowAllProgramsSelected()
    }

    override fun onControllerSetupFinished() {
        // TODO
    }
}
