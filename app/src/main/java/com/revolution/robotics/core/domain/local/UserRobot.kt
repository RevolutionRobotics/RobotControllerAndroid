package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Entity
@Parcelize
@Suppress("DataClassContainsFunctions")
data class UserRobot(
    @PrimaryKey(autoGenerate = true) var instanceId: Int = 0,
    var id: String? = null,
    var buildStatus: BuildStatus? = null,
    var actualBuildStep: Int = 0,
    var lastModified: Date? = null,
    var configurationId: Int = 0,
    var name: String? = null,
    var coverImage: String? = null,
    var description: String? = null
) : Parcelable {

    fun isCustomBuild() = id == null
}

@Dao
interface UserRobotDao {

    @Query("SELECT * FROM UserRobot ORDER BY lastModified DESC")
    fun getAllRobots(): List<UserRobot>

    @Query("SELECT * FROM UserRobot WHERE instanceId=:robotId")
    fun getRobotById(robotId: Int): UserRobot?

    @Query("SELECT * FROM UserRobot WHERE id=:robotId AND buildStatus=:buildStatus")
    fun getRobotByStatus(robotId: Int, buildStatus: BuildStatus): UserRobot?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserRobot(userRobot: UserRobot): Long

    @Update
    fun updateUserRobot(userRobot: UserRobot)

    @Query("DELETE FROM UserRobot WHERE instanceId = :id")
    fun deleteRobotById(id: Int)
}
