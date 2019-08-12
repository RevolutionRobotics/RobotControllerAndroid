package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Entity
import com.revolution.robotics.core.domain.PortMapping
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.features.configure.MotorPort
import com.revolution.robotics.features.configure.SensorPort
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class UserMapping(var userConfigId: Int = 0) : PortMapping(), Parcelable {

    fun isUsedVariableName(name: String, portName: String): Boolean = collectVariableNames(portName).contains(name)

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

    fun getDefaultUltrasonicName(): String {
        var count = 0
        if (S1?.type == Sensor.TYPE_ULTRASONIC) count++
        if (S2?.type == Sensor.TYPE_ULTRASONIC) count++
        if (S3?.type == Sensor.TYPE_ULTRASONIC) count++
        if (S4?.type == Sensor.TYPE_ULTRASONIC) count++
        return if (count == 0) {
            "distance"
        } else {
            "distance${count + 1}"
        }
    }

    fun getDefaultBumperName(): String {
        var count = 0
        if (S1?.type == Sensor.TYPE_BUMPER) count++
        if (S2?.type == Sensor.TYPE_BUMPER) count++
        if (S3?.type == Sensor.TYPE_BUMPER) count++
        if (S4?.type == Sensor.TYPE_BUMPER) count++

        return if (count == 0) {
            "bumper"
        } else {
            "bumper${count + 1}"
        }
    }

    private fun collectVariableNames(excludedPortName: String): List<String> {
        val variableNames = getVariables().toMutableList()
        excludeVariableName(excludedPortName, variableNames)
        return variableNames.mapNotNull { it }
    }

    @Suppress("ComplexMethod")
    private fun excludeVariableName(portName: String, variableNames: MutableList<String>) {
        when (portName) {
            "S1" -> variableNames.remove(S1?.variableName)
            "S2" -> variableNames.remove(S2?.variableName)
            "S3" -> variableNames.remove(S3?.variableName)
            "S4" -> variableNames.remove(S4?.variableName)
            "M1" -> variableNames.remove(M1?.variableName)
            "M2" -> variableNames.remove(M2?.variableName)
            "M3" -> variableNames.remove(M3?.variableName)
            "M4" -> variableNames.remove(M4?.variableName)
            "M5" -> variableNames.remove(M5?.variableName)
            "M6" -> variableNames.remove(M6?.variableName)
        }
    }
}
