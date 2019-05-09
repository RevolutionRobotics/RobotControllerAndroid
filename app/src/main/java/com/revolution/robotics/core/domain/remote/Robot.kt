package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Robot(
    var id: Int = 0,
    var name: String? = null,
    var description: String? = null,
    var coverImage: String? = null,
    var configurationId: Int = 0,
    var defaultProgram: String? = null,
    var buildTime: String? = null
) : Parcelable
