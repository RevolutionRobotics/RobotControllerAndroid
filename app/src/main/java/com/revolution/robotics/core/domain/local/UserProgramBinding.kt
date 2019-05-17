package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProgramBinding(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var controllerId: String,
    var programId: Int = 0,
    var priority: Int = 0
) : Parcelable
