package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.android.parcel.Parcelize

@Entity(
    indices = [Index(value = ["controllerId"])],
    foreignKeys = [ForeignKey(
        entity = UserController::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("controllerId"),
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
data class UserBackgroundProgramBinding(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var controllerId: Int = 0,
    var programId: String = "",
    var priority: Int = 0
) : Parcelable

@Dao
interface UserBackgroundProgramBindingDao {

    @Query("SELECT * FROM UserBackgroundProgramBinding WHERE controllerId=:controllerId")
    fun getBackgroundPrograms(controllerId: Int): List<UserBackgroundProgramBinding>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBackgroundPrograms(backgroundProgramBindings: List<UserBackgroundProgramBinding>): List<Long>

    @Query("DELETE FROM UserBackgroundProgramBinding WHERE controllerId=:controllerId")
    fun removeOldBackgroundBindings(controllerId: Int)

    @Query("DELETE FROM UserBackgroundProgramBinding WHERE programId=:programId")
    fun removeBackgroundProgram(programId: String)
}
