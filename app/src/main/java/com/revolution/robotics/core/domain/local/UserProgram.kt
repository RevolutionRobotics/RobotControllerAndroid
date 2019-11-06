package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.*
import com.revolution.robotics.core.extensions.formatYearMonthDay
import kotlinx.android.parcel.Parcelize

@Suppress("DataClassContainsFunctions")
@Entity(primaryKeys= [ "name", "robotId" ],
    foreignKeys = [ForeignKey(entity = UserRobot::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("robotId"),
        onDelete = ForeignKey.CASCADE)])
@Parcelize
data class UserProgram(
    var description: String? = null,
    var lastModified: Long = 0,
    var name: String = "",
    var python: String? = null,
    var xml: String? = null,
    var robotId: Int,
    var variables: List<String> = emptyList(),
    var remoteId: String? = null
) : Parcelable {
    fun getFormattedDate() = lastModified.formatYearMonthDay()
}

@Dao
interface UserProgramDao {

    @Query("SELECT * FROM UserProgram WHERE name=:name")
    fun getUserProgram(name: String): UserProgram?

    @Query("SELECT * FROM UserProgram ORDER BY lastModified DESC")
    fun getAllPrograms(): List<UserProgram>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserProgram(userProgram: UserProgram)

    @Query("DELETE FROM UserProgram WHERE name=:name")
    fun removeUserProgram(name: String)

    @Query("SELECT * FROM UserProgram WHERE remoteId=:remoteId")
    fun getUserProgramBasedOnRemoteId(remoteId: String): UserProgram?

    @Query("SELECT * FROM UserProgram WHERE robotId=:robotId ORDER BY lastModified DESC")
    fun getUserProgramsForRobot(robotId: Int): List<UserProgram>
}
