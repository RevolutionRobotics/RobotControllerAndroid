package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class UserConfiguration(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var controller: Int? = null,
    @Embedded
    var mappingId: UserMapping? = null
) : Parcelable

@Dao
interface UserConfigurationDao {

    @Query("SELECT * FROM UserConfiguration WHERE id=:id")
    fun getUserConfiguration(id: Int): UserConfiguration?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserConfiguration(userConfiguration: UserConfiguration): Long

    @Update
    fun updateUserConfiguration(userConfiguration: UserConfiguration): Int

    @Query("DELETE FROM UserConfiguration WHERE id=:id")
    fun deleteConfiguration(id: Int)
}
