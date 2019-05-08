package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sensor(
    var testCodeId: Int = 0,
    var type: String? = null,
    var variableName: String? = null
) : Parcelable {
    companion object {
        const val TYPE_BUMPER = "bumper"
        const val TYPE_ULTRASOUND = "ultrasound"
    }
}
