package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.android.parcel.Parcelize

@Entity(
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
    var controllerId: Int,
    var programId: Int = 0,
    var priority: Int = 0
) : Parcelable

@Dao
interface UserBackgroundProgramBindingDao {

    @Query("SELECT * FROM UserBackgroundProgramBinding WHERE controllerId=:controllerId")
    fun getBackgroundPrograms(controllerId: Int): List<UserBackgroundProgramBinding>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBackgroundPrograms(backgroundProgramBindings: List<UserBackgroundProgramBinding>): List<Long>
}
