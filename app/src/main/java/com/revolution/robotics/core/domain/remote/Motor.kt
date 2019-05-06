package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Motor(
    var rotation: String? = null,
    var side: String? = null,
    var testCodeId: Int = 0,
    var type: String? = null,
    var variableName: String? = null,
    var direction: String? = null
) : Parcelable
