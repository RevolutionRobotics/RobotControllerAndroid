package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Entity
import com.revolution.robotics.core.domain.PortMapping
import com.revolution.robotics.features.configure.MotorPort
import com.revolution.robotics.features.configure.SensorPort
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class UserMapping(var userConfigId: Int = 0) : PortMapping(), Parcelable {

    fun updateSensorPort(sensorPort: SensorPort) {
        when (sensorPort.portName) {
            "S1" -> S1 = sensorPort.sensor
            "S2" -> S2 = sensorPort.sensor
            "S3" -> S3 = sensorPort.sensor
            "S4" -> S4 = sensorPort.sensor
        }
    }

    fun updateMotorPort(motorPort: MotorPort) {
        when (motorPort.portName) {
            "M1" -> M1 = motorPort.motor
            "M2" -> M2 = motorPort.motor
            "M3" -> M3 = motorPort.motor
            "M4" -> M4 = motorPort.motor
            "M5" -> M5 = motorPort.motor
            "M6" -> M6 = motorPort.motor
        }
    }

    @Suppress("MagicNumber")
    fun getMotorPortIndex(motorPort: String?): Int = when (motorPort) {
        "M1" -> 1
        "M2" -> 2
        "M3" -> 3
        "M4" -> 4
        "M5" -> 5
        "M6" -> 6
        else -> -1
    }

    @Suppress("MagicNumber")
    fun getSensorPortIndex(sensorPort: String?): Int = when (sensorPort) {
        "S1" -> 1
        "S2" -> 2
        "S3" -> 3
        "S4" -> 4
        else -> -1
    }

    fun getVariables() = listOfNotNull(
        S1?.variableName,
        S2?.variableName,
        S3?.variableName,
        S4?.variableName,

        M1?.variableName,
        M2?.variableName,
        M3?.variableName,
        M4?.variableName,
        M5?.variableName,
        M6?.variableName
    )
}
