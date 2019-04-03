package com.revolution.robotics.core.domain.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import java.util.Date

@Entity
data class UserRobot(
    @PrimaryKey var id: String = "",
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
    fun getAllRobot(): List<UserRobot>

    @Query("SELECT * FROM UserRobot WHERE id=:id")
    fun getRobot(id: String): UserRobot

    @Insert
    fun saveUserRobot(userRobot: UserRobot)

    @Query("DELETE FROM UserRobot WHERE id=:id")
    fun deleteUserRobot(id: String)
}
