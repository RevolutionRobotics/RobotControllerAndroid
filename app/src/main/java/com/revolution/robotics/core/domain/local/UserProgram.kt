package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class UserProgram(
    var bindingId: String? = null,
    var description: String? = null,
    var programId: String? = null,
    var lastModified: Long = 0,
    var name: String? = null,
    var python: String? = null,
    var xml: String? = null,
    var variables: List<String> = emptyList()
) : Parcelable