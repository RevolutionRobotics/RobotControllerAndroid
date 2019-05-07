package com.revolution.robotics.features.configure.sensor

import com.revolution.robotics.core.domain.remote.Sensor

class SensorConfigurationPresenter : SensorConfigurationMvp.Presenter {

    override var view: SensorConfigurationMvp.View? = null
    override var model: SensorConfigurationViewModel? = null

    override fun setSensor(sensor: Sensor, portName: String) {

    }

    override fun onEmptyButtonClicked() {

    }

    override fun onBumberButtonClicked() {

    }

    override fun onUltarsoundButtonClicked() {

    }

    override fun onTestButtonClcked() {

    }

    override fun onDoneButtonClicked() {

    }
}