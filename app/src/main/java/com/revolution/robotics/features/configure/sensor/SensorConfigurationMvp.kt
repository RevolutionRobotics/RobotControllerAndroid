package com.revolution.robotics.features.configure.sensor

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Sensor

interface SensorConfigurationMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(baseDialog: BaseDialog)
        fun showError(error: String)
    }

    interface Presenter : Mvp.Presenter<View, SensorConfigurationViewModel> {
        fun setSensor(sensor: Sensor, portName: String)
        fun onEmptyButtonClicked()
        fun onBumperButtonClicked()
        fun onUltrasoundButtonClicked()
        fun onTestButtonClicked()
        fun onDoneButtonClicked()
        fun onVariableNameChanged(name: String?)
    }
}
