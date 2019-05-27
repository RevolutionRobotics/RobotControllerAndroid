package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Part(
    var name: String? = null,
    var image: String? = null
) : Parcelable
