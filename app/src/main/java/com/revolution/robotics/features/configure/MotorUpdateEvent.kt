package com.revolution.robotics.features.configure

import com.revolution.robotics.core.domain.remote.Motor

data class MotorUpdateEvent(val motor: Motor, val portName: String?)