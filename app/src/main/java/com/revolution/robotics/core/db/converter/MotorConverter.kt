package com.revolution.robotics.core.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.revolution.robotics.core.domain.remote.Motor

class MotorConverter {

    @TypeConverter
    fun toMotor(motor: String): Motor = Gson().fromJson(motor, Motor::class.java)

    @TypeConverter
    fun fromMotor(motor: Motor): String = Gson().toJson(motor)
}
