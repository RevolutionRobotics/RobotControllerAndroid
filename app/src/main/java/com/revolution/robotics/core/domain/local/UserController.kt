package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.android.parcel.Parcelize

@Entity
@Suppress("DataClassContainsFunctions")
@Parcelize
data class UserController(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var description: String? = null,
    var lastModified: Long = 0L,
    var name: String? = null,
    var type: String? = null,
    @Embedded
    var mapping: UserButtonMapping? = UserButtonMapping()
) : Parcelable {

    fun getMappingList() = listOf(mapping?.b1, mapping?.b2, mapping?.b3, mapping?.b4, mapping?.b5, mapping?.b6)
}

@Dao
interface UserControllerDao {

    @Query("SELECT * FROM UserController WHERE id=:id")
    fun getUserController(id: Int): UserController?

    @Query("SELECT * FROM UserController ORDER BY lastModified")
    fun getUserControllers(): List<UserController>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserController(userController: UserController): Long

    @Query("DELETE FROM UserController WHERE id = :id")
    fun deleteUserControllerById(id: Int)
}
