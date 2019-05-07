package com.revolution.robotics.core.domain.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.revolution.robotics.core.domain.shared.RobotDescriptor
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Entity
@Parcelize
data class UserRobot(
    @PrimaryKey(autoGenerate = true) var instanceId: Int = 0,
    override var id: Int = 0,
    var buildStatus: BuildStatus? = null,
    var actualBuildStep: Int = 0,
    var lastModified: Date? = null,
    var configId: String? = null,
    override val name: String? = null,
    override var coverImage: String?,
    override var description: String?
) : RobotDescriptor

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
