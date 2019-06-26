package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Part(
    var order: Int = 0,
    var name: String? = null,
    var image: String? = null
) : Parcelable
