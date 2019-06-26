package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Robot(
    var id: String? = null,
    var name: String? = null,
    var order: Int = 0,
    var description: String? = null,
    var coverImage: String? = null,
    var configurationId: String? = null,
    var defaultProgram: String? = null,
    var buildTime: String? = null
) : Parcelable
