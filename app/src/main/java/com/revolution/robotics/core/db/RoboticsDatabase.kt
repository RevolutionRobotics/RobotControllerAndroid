package com.revolution.robotics.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.revolution.robotics.core.db.converter.BuildStatusConverter
import com.revolution.robotics.core.db.converter.DateConverter
import com.revolution.robotics.core.db.converter.MotorConverter
import com.revolution.robotics.core.db.converter.SensorConverter
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao

@Database(entities = [UserRobot::class, UserConfiguration::class], version = 1)
@TypeConverters(DateConverter::class, BuildStatusConverter::class, MotorConverter::class, SensorConverter::class)
abstract class RoboticsDatabase : RoomDatabase() {
    abstract fun userRobotDao(): UserRobotDao
    abstract fun userConfigurationDao(): UserConfigurationDao
}
