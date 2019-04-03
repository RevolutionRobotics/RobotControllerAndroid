package com.revolution.robotics.core.domain.local

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class UserConfiguration(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var controller: String? = null,
    @Embedded
    var mappingId: UserMapping? = null
)

@Dao
interface UserConfigurationDao {

    @Query("SELECT * FROM UserConfiguration WHERE id=:id")
    fun getUserConfiguration(id: Int): UserConfiguration

    @Insert
    fun saveUserConfiguration(userConfiguration: UserConfiguration)
}
