package com.revolution.robotics.core.domain.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import java.util.Date

@Entity
data class UserRobot(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var robotId: Int = 0,
    var buildStatus: BuildStatus? = null,
    var actualBuildStep: Int = 0,
    var lastModified: Date? = null,
    var configId: String? = null,
    var customName: String? = null,
    var customImage: String? = null,
    var customDescription: String? = null
)

@Dao
interface UserRobotDao {

    @Query("SELECT * FROM UserRobot")
    fun getAllRobots(): List<UserRobot>

    @Query("SELECT * FROM UserRobot WHERE robotId=:robotId AND buildStatus=:buildStatus")
    fun getRobotByStatus(robotId: Int, buildStatus: BuildStatus): UserRobot?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserRobot(userRobot: UserRobot)
}
