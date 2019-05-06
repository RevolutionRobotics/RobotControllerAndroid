package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.core.domain.remote.Motor

class MotorConfigurationPresenter : MotorConfigurationMvp.Presenter {
    override var view: MotorConfigurationMvp.View? = null
    override var model: MotorConfigurationViewModel? = null

    override fun setMotor(motor: Motor, portName: String) {

    }

    override fun onEmptyButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDrivetrainButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMotorClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLeftDirectionClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRightDirectionClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCounterClockwiseClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClockwiseClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}