package com.revolution.robotics.features.build.tips

import com.revolution.robotics.R

class SensorTipsDialogFace : TipsDialogFace() {

    override val bulletCharacter: Char = '-'
    override val tipsList: List<Int> = listOf(
        R.string.tips_dialog_placeholder_1,
        R.string.tips_dialog_placeholder_2,
        R.string.tips_dialog_placeholder_3,
        R.string.tips_dialog_placeholder_4
    )
}
