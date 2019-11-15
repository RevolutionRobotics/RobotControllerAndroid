package com.revolution.robotics.core.domain

import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor

@Suppress("ConstructorParameterNaming")
open class PortMapping(
    var S1: Sensor? = null,
    var S2: Sensor? = null,
    var S3: Sensor? = null,
    var S4: Sensor? = null,
    var M1: Motor? = null,
    var M2: Motor? = null,
    var M3: Motor? = null,
    var M4: Motor? = null,
    var M5: Motor? = null,
    var M6: Motor? = null
)
