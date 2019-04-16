package com.revolution.robotics.core.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor
import java.util.Date

class Converters {

    // Sensor
    @TypeConverter
    fun toSensor(sensor: String): Sensor = Gson().fromJson(sensor, Sensor::class.java)

    @TypeConverter
    fun fromSensor(sensor: Sensor): String = Gson().toJson(sensor)

    // Motor
    @TypeConverter
    fun toMotor(motor: String): Motor = Gson().fromJson(motor, Motor::class.java)

    @TypeConverter
    fun fromMotor(motor: Motor): String = Gson().toJson(motor)

    // Date
    @TypeConverter
    fun toDate(dateLong: Long): Date = Date(dateLong)

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    // BuildStatus
    @TypeConverter
    fun toBuildStatus(buildStatus: Int): BuildStatus = BuildStatus.values()[buildStatus]

    @TypeConverter
    fun fromBuildStatus(buildStatus: BuildStatus): Int = buildStatus.ordinal
}