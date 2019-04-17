package com.revolution.robotics.core.utils.chippedBox

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxDrawable.Companion.BOTTOM_LEFT_INDEX
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxDrawable.Companion.BOTTOM_RIGHT_INDEX
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxDrawable.Companion.TOP_LEFT_INDEX
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxDrawable.Companion.TOP_RIGHT_INDEX

data class ChippedBoxConfig(
    val chipSizeResource: Int,
    val chipBorderSizeResource: Int,
    @ColorRes val chipBackgroundColor: Int,
    @ColorRes val chipBorderColor: Int,
    val chipTopLeft: Boolean,
    val chipTopRight: Boolean,
    val chipBottomRight: Boolean,
    val chipBottomLeft: Boolean,
    val clipLeftSide: Boolean,
    val clipRightSide: Boolean
) {

    val chipArray = arrayListOf(0, 0, 0, 0)

    init {
        if (chipTopLeft) chipArray[TOP_LEFT_INDEX] = chipSizeResource
        if (chipTopRight) chipArray[TOP_RIGHT_INDEX] = chipSizeResource
        if (chipBottomRight) chipArray[BOTTOM_RIGHT_INDEX] = chipSizeResource
        if (chipBottomLeft) chipArray[BOTTOM_LEFT_INDEX] = chipSizeResource
    }

    @Suppress("UnnecessaryApply", "TooManyFunctions")
    class Builder {
        private var chipSizeResource = 0
        private var chipBorderSize = 0
        private var chipBackgroundColorResource = 0
        private var chipBorderColorResource = 0
        private var chipTopLeft = false
        private var chipTopRight = true
        private var chipBottomRight = false
        private var chipBottomLeft = true
        private var clipLeftSide = false
        private var clipRightSide = false

        fun chipSize(@DimenRes size: Int) = apply { chipSizeResource = size }
        fun borderSize(@DimenRes size: Int) = apply { chipBorderSize = size }
        fun backgroundColorResource(@ColorRes colorRes: Int) = apply { chipBackgroundColorResource = colorRes }
        fun borderColorResource(@ColorRes colorRes: Int) = apply { chipBorderColorResource = colorRes }
        fun chipTopLeft(chip: Boolean = true) = apply { chipTopLeft = chip }
        fun chipTopRight(chip: Boolean = true) = apply { chipTopRight = chip }
        fun chipBottomRight(chip: Boolean = true) = apply { chipBottomRight = chip }
        fun chipBottomLeft(chip: Boolean = true) = apply { chipBottomLeft = chip }
        fun clipLeftSide(clip: Boolean = true) = apply { clipLeftSide = clip }
        fun clipRightSide(clip: Boolean = true) = apply { clipRightSide = clip }

        fun create() =
            ChippedBoxConfig(
                chipSizeResource,
                chipBorderSize,
                chipBackgroundColorResource,
                chipBorderColorResource,
                chipTopLeft,
                chipTopRight,
                chipBottomRight,
                chipBottomLeft,
                clipLeftSide,
                clipRightSide
            )
    }
}
