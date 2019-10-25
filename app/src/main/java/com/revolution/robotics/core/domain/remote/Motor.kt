package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Motor(
    var reversed: Boolean = false,
    var side: String? = null,
    var type: String? = null,
    var variableName: String? = null
) : Parcelable {

    companion object {
        const val SIDE_LEFT = "left"
        const val SIDE_RIGHT = "right"

        const val TYPE_MOTOR = "motor"
        const val TYPE_DRIVETRAIN = "drive"
    }
}
