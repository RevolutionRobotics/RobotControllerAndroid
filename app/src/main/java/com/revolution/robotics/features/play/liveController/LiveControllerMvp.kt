package com.revolution.robotics.features.play.liveController

import com.revolution.robotics.core.Mvp

// TODO delete this class
interface LiveControllerMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, LiveControllerViewModel> {
        fun onButtonClicked(index: Int)
        fun onXAxisChanged(value: Int)
        fun onYAxisChanged(value: Int)
    }
}
