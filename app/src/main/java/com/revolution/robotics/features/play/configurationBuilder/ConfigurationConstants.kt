package com.revolution.robotics.features.play.configurationBuilder

import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor

class ConfigurationConstants private constructor() {
    // Utility class

    companion object {
        const val INVALID_DATA = -1

        private val CONSTANTS = hashMapOf(
            Motor.TYPE_MOTOR to 1,
            Motor.TYPE_DRIVETRAIN to 2,
            Motor.SIDE_LEFT to 0,
            Motor.SIDE_RIGHT to 1,
            Motor.DIRECTION_CLOCKWISE to 0,
            Motor.DIRECTION_COUNTER_CLOCKWISE to 1,

            Sensor.TYPE_ULTRASONIC to 1,
            Sensor.TYPE_BUMPER to 2
        )

        fun get(value: String?) =
            CONSTANTS[value] ?: INVALID_DATA
    }
}
