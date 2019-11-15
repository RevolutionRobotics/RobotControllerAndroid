package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProgramBinding(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var controllerId: Int,
    var programName: String = "",
    var priority: Int = 0
) : Parcelable
