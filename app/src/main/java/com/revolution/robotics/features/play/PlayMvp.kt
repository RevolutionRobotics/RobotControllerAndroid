package com.revolution.robotics.features.play

import com.revolution.robotics.core.Mvp

interface PlayMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, PlayViewModel> {
        fun onJoystickEvent()
        fun onButtonPressed(ordinal: Int)
    }
}