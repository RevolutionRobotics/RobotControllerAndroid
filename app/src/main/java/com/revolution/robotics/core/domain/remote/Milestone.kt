package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Milestone(
    var testImage: String? = null,
    var testDescription: LocalizedString? = null,
    var testCode: String? = null,
    var type: String? = null
) : Parcelable
