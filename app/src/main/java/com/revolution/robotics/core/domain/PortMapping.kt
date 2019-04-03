package com.revolution.robotics.core.domain

import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor

@Suppress("VariableNaming")
open class PortMapping {
    constructor()

    constructor(portMapping: PortMapping) {
        S1 = portMapping.S1
        S2 = portMapping.S2
        S3 = portMapping.S3
        S4 = portMapping.S4

        M1 = portMapping.M1
        M2 = portMapping.M2
        M3 = portMapping.M3
        M4 = portMapping.M4
        M5 = portMapping.M5
        M6 = portMapping.M6
    }

    var S1: Sensor? = null
    var S2: Sensor? = null
    var S3: Sensor? = null
    var S4: Sensor? = null
    var M1: Motor? = null
    var M2: Motor? = null
    var M3: Motor? = null
    var M4: Motor? = null
    var M6: Motor? = null
    var M5: Motor? = null
}