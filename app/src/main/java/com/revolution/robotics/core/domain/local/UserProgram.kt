package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class UserProgram(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var description: String? = null,
    var lastModified: Long = 0,
    var name: String? = null,
    var python: String? = null,
    var xml: String? = null,
    var variables: List<String> = emptyList(),
    var remoteId: String? = null
) : Parcelable

@Dao
interface UserProgramDao {

    @Query("SELECT * FROM UserProgram WHERE id=:id")
    fun getUserProgram(id: Int): UserProgram?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserProgram(userProgram: UserProgram): Long

    @Query("DELETE FROM UserProgram WHERE id=:id")
    fun removeUserProgram(id: Int)

    @Query("SELECT * FROM UserProgram WHERE remoteId=:remoteId")
    fun getUserProgramBasedOnRemoteId(remoteId: String): UserProgram?
}
