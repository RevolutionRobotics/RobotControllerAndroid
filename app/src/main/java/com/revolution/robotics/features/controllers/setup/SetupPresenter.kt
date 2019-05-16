package com.revolution.robotics.features.controllers.setup

class SetupPresenter : SetupMvp.Presenter {

    override var view: SetupMvp.View? = null
    override var model: SetupViewModel? = null

    override fun onProgramSlotSelected(index: Int, viewModel: SetupViewModel) {
        view?.onProgramSlotSelected(index, viewModel)
    }
}