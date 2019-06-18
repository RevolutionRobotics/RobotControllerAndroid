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
import androidx.room.Update
import kotlinx.android.parcel.Parcelize

@Entity(
    foreignKeys = [ForeignKey(
        entity = UserRobot::class,
        parentColumns = arrayOf("instanceId"),
        childColumns = arrayOf("robotId"),
        onDelete = ForeignKey.CASCADE
    )]
)
@Suppress("DataClassContainsFunctions")
@Parcelize
data class UserController(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var robotId: Int = 0,
    var name: String? = null,
    var type: String? = null,
    var description: String? = null,
    var lastModified: Long = 0L,
    @Embedded
    var mapping: UserButtonMapping? = UserButtonMapping()
) : Parcelable {

    fun getMappingList() = listOf(mapping?.b1, mapping?.b2, mapping?.b3, mapping?.b4, mapping?.b5, mapping?.b6)
}

@Dao
interface UserControllerDao {

    @Query("SELECT * FROM UserController")
    fun getUserControllers(): List<UserController>

    @Query("SELECT * FROM UserController WHERE id=:id")
    fun getUserController(id: Int): UserController?

    @Query("SELECT * FROM UserController WHERE robotId=:robotId ORDER BY lastModified")
    fun getUserControllersForRobot(robotId: Int): List<UserController>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun saveUserController(userController: UserController): Long

    @Update
    fun updateUserController(userController: UserController)

    @Query("DELETE FROM UserController WHERE id = :id")
    fun deleteUserControllerById(id: Int)
}
