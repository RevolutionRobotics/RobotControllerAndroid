package com.revolution.robotics.features.configure.controller.program.priority

import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class ProgramPriorityJoystickViewModel(
    position: Int
) : PriorityItem(position) {

    override val idField: Any = "Joystick"

    val background = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.grey_1d)
        .borderColorResource(R.color.grey_8e)
        .chipBottomLeft(true)
        .chipTopRight(true)
        .chipSize(R.dimen.dimen_6dp)
        .clipLeftSide(true)
        .borderSize(R.dimen.dimen_1dp)
        .create()
    val positionText: String
        get() {
            return "$position."
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProgramPriorityItemViewModel
        if (position != other.position) return false
        return true
    }

    override fun hashCode(): Int = position.hashCode()
}
