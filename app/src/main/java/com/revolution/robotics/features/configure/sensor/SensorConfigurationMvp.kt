package com.revolution.robotics.features.configure.sensor

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Sensor

interface SensorConfigurationMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, SensorConfigurationViewModel> {
        fun setSensor(sensor: Sensor, portName: String)
        fun onEmptyButtonClicked()
        fun onBumberButtonClicked()
        fun onUltarsoundButtonClicked()

        fun onTestButtonClcked()
        fun onDoneButtonClicked()
    }
}
