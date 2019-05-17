package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.core.Mvp

interface SetupMvp : Mvp {

    interface View : Mvp.View {
        fun onProgramSlotSelected(index: Int, viewModel: SetupViewModel)
    }

    interface Presenter : Mvp.Presenter<View, SetupViewModel> {
        fun onProgramSlotSelected(index: Int, viewModel: SetupViewModel)
        fun onControllerSetupFinished()
    }
}
