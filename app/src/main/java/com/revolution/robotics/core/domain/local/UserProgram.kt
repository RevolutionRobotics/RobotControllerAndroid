package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.revolution.robotics.core.extensions.formatYearMonthDay
import kotlinx.android.parcel.Parcelize

@Suppress("DataClassContainsFunctions")
@Entity
@Parcelize
data class UserProgram(
    var description: String? = null,
    var lastModified: Long = 0,
    @PrimaryKey
    var name: String = "",
    var python: String? = null,
    var xml: String? = null,
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
}
