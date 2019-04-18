package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Milestone(
    var image: String? = null,
    var testCodeId: Int = 0,
    var type: String? = null
) : Parcelable
