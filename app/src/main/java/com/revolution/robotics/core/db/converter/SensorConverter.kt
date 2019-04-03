package com.revolution.robotics.core.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.revolution.robotics.core.domain.remote.Sensor

class SensorConverter {
    @TypeConverter
    fun toSensor(sensor: String): Sensor = Gson().fromJson(sensor, Sensor::class.java)

    @TypeConverter
    fun fromSensor(sensor: Sensor): String = Gson().toJson(sensor)
}
