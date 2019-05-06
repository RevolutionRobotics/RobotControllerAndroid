package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Motor

interface MotorConfigurationMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, MotorConfigurationViewModel> {
        fun setMotor(motor: Motor)
    }
}