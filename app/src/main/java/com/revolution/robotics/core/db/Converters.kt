package com.revolution.robotics.core.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserProgramBinding
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor
import java.util.Date

class Converters {

    // Sensor
    @TypeConverter
    fun toSensor(sensor: String): Sensor? = Gson().fromJson(sensor, Sensor::class.java)

    @TypeConverter
    fun fromSensor(sensor: Sensor?): String = Gson().toJson(sensor)

    // Motor
    @TypeConverter
    fun toMotor(motor: String?): Motor? = Gson().fromJson(motor, Motor::class.java)

    @TypeConverter
    fun fromMotor(motor: Motor?): String = Gson().toJson(motor)

    // UserProgramBinding
    @TypeConverter
    fun toUserProgramBinding(userProgramBinding: String?): UserProgramBinding? =
        Gson().fromJson(userProgramBinding, UserProgramBinding::class.java)

    @TypeConverter
    fun fromUserProgramBinding(userProgramBinding: UserProgramBinding?): String = Gson().toJson(userProgramBinding)

    // Date
    @TypeConverter
    fun toDate(dateLong: Long): Date = Date(dateLong)

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    // BuildStatus
    @TypeConverter
    fun toBuildStatus(buildStatus: String): BuildStatus = BuildStatus.valueOf(buildStatus)

    @TypeConverter
    fun fromBuildStatus(buildStatus: BuildStatus): String = buildStatus.name

    // Variables list
    @TypeConverter
    fun toVariables(variables: String): List<String> = variables.split(",")

    @TypeConverter
    fun fromVariables(variables: List<String>): String = variables.joinToString(separator = ",")
}
