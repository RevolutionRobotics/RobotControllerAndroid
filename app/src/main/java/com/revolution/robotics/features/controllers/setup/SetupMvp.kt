package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.core.Mvp

interface SetupMvp : Mvp {

    interface View : Mvp.View {
        fun onProgramSlotSelected(index: Int)
    }

    interface Presenter : Mvp.Presenter<View, SetupViewModel> {
        fun onProgramSlotSelected(index: Int)
        fun onControllerSetupFinished()
    }
}
