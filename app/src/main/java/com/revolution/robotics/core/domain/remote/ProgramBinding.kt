package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class ProgramBinding(
    var program: String? = null,
    var priority: Int = 0
) : Parcelable
