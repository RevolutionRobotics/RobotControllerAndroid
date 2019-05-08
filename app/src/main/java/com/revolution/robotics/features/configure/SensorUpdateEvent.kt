package com.revolution.robotics.features.configure

import com.revolution.robotics.core.domain.remote.Sensor

data class SensorUpdateEvent(val sensor: Sensor, val portName: String?)