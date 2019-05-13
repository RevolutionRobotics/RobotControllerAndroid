package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Entity
@Parcelize
data class UserRobot(
    @PrimaryKey(autoGenerate = true) var instanceId: Int = 0,
    var id: Int = 0,
    var buildStatus: BuildStatus? = null,
    var actualBuildStep: Int = 0,
    var lastModified: Date? = null,
    var configurationId: Int = 0,
    var name: String? = null,
    var coverImage: String? = null,
    var description: String? = null
) : Parcelable

@Dao
interface UserRobotDao {

    @Query("SELECT * FROM UserRobot")
    fun getAllRobots(): List<UserRobot>

    @Query("SELECT * FROM UserRobot WHERE id=:robotId AND buildStatus=:buildStatus")
    fun getRobotByStatus(robotId: Int, buildStatus: BuildStatus): UserRobot?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserRobot(userRobot: UserRobot)

    @Query("DELETE FROM UserRobot WHERE instanceId = :id")
    fun deleteRobotById(id: Int)
}
