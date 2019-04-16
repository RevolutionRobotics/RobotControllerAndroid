package com.revolution.robotics.core.utils.chippedBox

import androidx.annotation.ColorRes
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxDrawable.Companion.BOTTOM_LEFT_INDEX
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxDrawable.Companion.BOTTOM_RIGHT_INDEX
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxDrawable.Companion.TOP_LEFT_INDEX
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxDrawable.Companion.TOP_RIGHT_INDEX

data class ChippedBoxConfig(
    val chipSize: Float = 0f,
    val chipBorderSize: Float = 0f,
    @ColorRes val chipBackgroundColor: Int = 0,
    @ColorRes val chipBorderColor: Int = 0,
    val chipTopLeft: Boolean = false,
    val chipTopRight: Boolean = false,
    val chipBottomRight: Boolean = false,
    val chipBottomLeft: Boolean = false,
    val clipLeftSide: Boolean = false,
    val clipRightSide: Boolean = false
) {
    val chipArray = arrayListOf(0f, 0f, 0f, 0f)

    constructor(
        chipSize: Float? = null,
        chipBorderSize: Float? = null,
        @ColorRes chipBackgroundColor: Int? = null,
        @ColorRes chipBorderColor: Int? = null,
        chipTopLeft: Boolean? = null,
        chipTopRight: Boolean? = null,
        chipBottomRight: Boolean? = null,
        chipBottomLeft: Boolean? = null,
        clipLeftSide: Boolean? = null,
        clipRightSide: Boolean? = null
    ) : this(
        chipSize ?: 0f,
        chipBorderSize ?: 0f,
        chipBackgroundColor ?: 0,
        chipBorderColor ?: 0,
        chipTopLeft ?: false,
        chipTopRight ?: false,
        chipBottomRight ?: false,
        chipBottomLeft ?: false,
        clipLeftSide ?: false,
        clipRightSide ?: false
    )

    init {
        if (chipTopLeft) chipArray[TOP_LEFT_INDEX] = chipSize
        if (chipTopRight) chipArray[TOP_RIGHT_INDEX] = chipSize
        if (chipBottomRight) chipArray[BOTTOM_RIGHT_INDEX] = chipSize
        if (chipBottomLeft) chipArray[BOTTOM_LEFT_INDEX] = chipSize
    }
}
