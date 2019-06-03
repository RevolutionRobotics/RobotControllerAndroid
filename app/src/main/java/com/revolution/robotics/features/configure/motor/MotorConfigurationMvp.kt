package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Motor

interface MotorConfigurationMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(baseDialog: BaseDialog)
    }

    @Suppress("ComplexInterface", "TooManyFunctions")
    interface Presenter : Mvp.Presenter<View, MotorConfigurationViewModel> {
        fun setMotor(motor: Motor, portName: String)
        fun onVariableNameChanged(name: String?)
        fun onEmptyButtonClicked()
        fun onDrivetrainButtonClicked()
        fun onMotorClicked()
        fun onLeftSideClicked()
        fun onRightSideClicked()
        fun onCounterClockwiseClicked()
        fun onClockwiseClicked()
        fun onTestButtonClicked()
        fun onDoneButtonClicked()
    }
}
