package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserConfiguration(
    var controller: Int? = null,
    @Embedded
    var mappingId: UserMapping? = null
) : Parcelable