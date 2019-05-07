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
) : Parcelable {
    companion object {
        const val SIDE_LEFT = "left"
        const val SIDE_RIGHT = "right"

        const val ROTATION_CLOCKWISE = "clockwise"
        const val ROTATION_COUNTER_CLOCKWISE = "counterclockwise"

        const val TYPE_MOTOR = "motor"
        const val TYPE_DRIVETRAIN = "drivetrain"
    }
}
