package com.revolution.robotics.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao

@Database(entities = [UserRobot::class, UserConfiguration::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RoboticsDatabase : RoomDatabase() {
    abstract fun userRobotDao(): UserRobotDao
    abstract fun userConfigurationDao(): UserConfigurationDao
}
