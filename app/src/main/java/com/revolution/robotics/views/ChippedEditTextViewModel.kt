package com.revolution.robotics.views

import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

@Suppress("DataClassContainsFunctions")
data class ChippedEditTextViewModel(
    var title: String? = null,
    var text: String? = null,
    var hint: String? = null,
    var titleColor: Int = R.color.black,
    var textColor: Int = R.color.white,
    var hintColor: Int = R.color.white,
    var borderColor: Int = R.color.white,
    var backgroundColor: Int = R.color.white,
    var textMaxLines: Int = CHIPPED_EDIT_TEXT_DEFAULT_MAX_LINES,
    var textMaxLength: Int = CHIPPED_EDIT_TEXT_DEFAULT_MAX_LENGTH
) {
    companion object {
        const val CHIPPED_EDIT_TEXT_DEFAULT_MAX_LINES = 2
        const val CHIPPED_EDIT_TEXT_DEFAULT_MAX_LENGTH = 25
    }

    fun generateChipConfig(): ChippedBoxConfig = ChippedBoxConfig
        .Builder()
        .apply {
            if (backgroundColor != 0) {
                backgroundColorResource(backgroundColor)
            }
            if (borderColor != 0) {
                borderColorResource(borderColor)
            }
        }
        .borderSize(R.dimen.dimen_1dp)
        .chipTopRight(true)
        .chipBottomLeft(true)
        .chipSize(R.dimen.dimen_8dp)
        .create()

}