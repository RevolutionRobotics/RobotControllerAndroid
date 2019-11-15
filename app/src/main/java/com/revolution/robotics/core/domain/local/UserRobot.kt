package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Entity
@Parcelize
@Suppress("DataClassContainsFunctions")
data class UserRobot(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var remoteId: String? = null,
    var buildStatus: BuildStatus? = null,
    var actualBuildStep: Int = 0,
    var lastModified: Date? = null,
    @Embedded
    var configuration: UserConfiguration,
    var name: String? = null,
    var coverImage: String? = null,
    var description: String? = null
) : Parcelable {

    fun isCustomBuild() = remoteId == null
}

@Dao
interface UserRobotDao {

    @Query("SELECT * FROM UserRobot ORDER BY lastModified DESC")
    fun getAllRobots(): List<UserRobot>

    @Query("SELECT * FROM UserRobot WHERE id=:robotId")
    fun getRobotById(robotId: Int): UserRobot?

    @Query("SELECT * FROM UserRobot WHERE remoteId=:robotId AND buildStatus=:buildStatus")
    fun getRobotByStatus(robotId: Int, buildStatus: BuildStatus): UserRobot?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserRobot(userRobot: UserRobot): Long

    @Update
    fun updateUserRobot(userRobot: UserRobot)

    @Query("DELETE FROM UserRobot WHERE id = :id")
    fun deleteRobotById(id: Int)
}
