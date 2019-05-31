package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Milestone(
    var image: String? = null,
    var testImage: String? = null,
    var testDescription: String? = null,
    var testCode: String? = null,
    var type: String? = null
) : Parcelable
