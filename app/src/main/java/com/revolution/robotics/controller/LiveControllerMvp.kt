package com.revolution.robotics.controller

import com.revolution.robotics.Mvp

interface LiveControllerMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, LiveControllerViewModel> {
        fun onButtonClicked(index: Int)
        fun onXAxisChanged(value: Int)
        fun onYAxisChanged(value: Int)
    }
}
