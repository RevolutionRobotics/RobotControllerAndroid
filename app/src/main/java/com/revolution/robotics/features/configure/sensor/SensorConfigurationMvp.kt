package com.revolution.robotics.features.configure.sensor

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.views.dialogs.RoboticsDialog

interface SensorConfigurationMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(roboticsDialog: RoboticsDialog)
    }

    interface Presenter : Mvp.Presenter<View, SensorConfigurationViewModel> {
        fun setSensor(sensor: Sensor, portName: String)
        fun onEmptyButtonClicked()
        fun onBumperButtonClicked()
        fun onUltrasoundButtonClicked()
        fun onTestButtonClicked()
        fun onDoneButtonClicked()
    }
}
