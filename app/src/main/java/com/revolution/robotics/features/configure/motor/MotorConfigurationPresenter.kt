package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.core.domain.remote.Motor

class MotorConfigurationPresenter : MotorConfigurationMvp.Presenter {

    override var view: MotorConfigurationMvp.View? = null
    override var model: MotorConfigurationViewModel? = null

    override fun setMotor(motor: Motor) {
        // TODO init motor
    }
}