package com.revolution.robotics.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.revolution.robotics.core.domain.local.*

@Database(
    entities =
    [UserRobot::class,
        UserController::class,
        UserBackgroundProgramBinding::class,
        UserProgram::class,
        UserChallengeCategory::class],
    version = 20,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RoboticsDatabase : RoomDatabase() {
    abstract fun userRobotDao(): UserRobotDao
    abstract fun userControllerDao(): UserControllerDao
    abstract fun userBackgroundProgramBindingDao(): UserBackgroundProgramBindingDao
    abstract fun userProgramDao(): UserProgramDao
    abstract fun userChallengeCategoryDao(): UserChallengeCategoryDao
}
