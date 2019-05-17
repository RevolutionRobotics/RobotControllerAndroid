package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.android.parcel.Parcelize

@Entity(
    foreignKeys = [ForeignKey(
        entity = UserConfiguration::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("configId"),
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
data class UserController(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var description: String? = null,
    var lastModified: Long = 0L,
    var name: String? = null,
    var type: String? = null,
    var configId: Int = 0,
    @Embedded
    var mapping: UserButtonMapping? = null
) : Parcelable

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