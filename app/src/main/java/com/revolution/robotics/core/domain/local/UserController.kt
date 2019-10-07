package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import com.revolution.robotics.features.configure.controller.ControllerButton
import kotlinx.android.parcel.Parcelize

@Entity(
    foreignKeys = [ForeignKey(
        entity = UserRobot::class,
        parentColumns = arrayOf("instanceId"),
        childColumns = arrayOf("robotId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["robotId"])]
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
    var joystickPriority: Int = 0,
    @Embedded
    var mapping: UserButtonMapping? = UserButtonMapping()
) : Parcelable {

    fun getMappingList() = listOf(mapping?.b1, mapping?.b2, mapping?.b3, mapping?.b4, mapping?.b5, mapping?.b6)

    fun getBoundButtonPrograms() = getMappingList().filterNotNull()

    fun removeProgramMapping(programName: String) {
        if (mapping?.b1?.programName == programName) {
            mapping?.b1 = null
        }
        if (mapping?.b2?.programName == programName) {
            mapping?.b2 = null
        }
        if (mapping?.b3?.programName == programName) {
            mapping?.b3 = null
        }
        if (mapping?.b4?.programName == programName) {
            mapping?.b4 = null
        }
        if (mapping?.b5?.programName == programName) {
            mapping?.b5 = null
        }
        if (mapping?.b6?.programName == programName) {
            mapping?.b6 = null
        }
    }

    fun addButtonProgram(userProgram: UserProgram, buttonName: ControllerButton) {
        when (buttonName) {
            ControllerButton.B1 -> mapping?.b1 = UserProgramBinding(0, id, userProgram.name, nextPriority())
            ControllerButton.B2 -> mapping?.b2 = UserProgramBinding(0, id, userProgram.name, nextPriority())
            ControllerButton.B3 -> mapping?.b3 = UserProgramBinding(0, id, userProgram.name, nextPriority())
            ControllerButton.B4 -> mapping?.b4 = UserProgramBinding(0, id, userProgram.name, nextPriority())
            ControllerButton.B5 -> mapping?.b5 = UserProgramBinding(0, id, userProgram.name, nextPriority())
            ControllerButton.B6 -> mapping?.b6 = UserProgramBinding(0, id, userProgram.name, nextPriority())
        }
    }

    fun removeButtonProgram(buttonName: ControllerButton) {
        when (buttonName) {
            ControllerButton.B1 -> mapping?.b1 = null
            ControllerButton.B2 -> mapping?.b2 = null
            ControllerButton.B3 -> mapping?.b3 = null
            ControllerButton.B4 -> mapping?.b4 = null
            ControllerButton.B5 -> mapping?.b5 = null
            ControllerButton.B6 -> mapping?.b6 = null
        }
    }

    private fun nextPriority() = (listOfNotNull(
        mapping?.b1?.priority,
        mapping?.b2?.priority,
        mapping?.b3?.priority,
        mapping?.b4?.priority,
        mapping?.b5?.priority,
        mapping?.b6?.priority
    ).max() ?: 0) + 1
}

@Dao
interface UserControllerDao {

    @Query("SELECT * FROM UserController")
    fun getUserControllers(): List<UserController>

    @Query("SELECT * FROM UserController WHERE id=:id")
    fun getUserController(id: Int): UserController?

    @Query("SELECT * FROM UserController WHERE robotId=:robotId")
    fun getUserControllerForRobot(robotId: Int): UserController?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun saveUserController(userController: UserController): Long

    @Update
    fun updateUserController(userController: UserController)

    @Query("DELETE FROM UserController WHERE id = :id")
    fun deleteUserControllerById(id: Int)
}
