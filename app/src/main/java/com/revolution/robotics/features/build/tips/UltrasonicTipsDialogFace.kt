package com.revolution.robotics.features.build.tips

import com.revolution.robotics.R
import com.revolution.robotics.features.build.testing.TestDialog

class UltrasonicTipsDialogFace(source: TestDialog.Source, dialogController: DialogController) : TipsDialogFace() {

    override val bulletCharacter: Char = '-'
    override val tipsList: List<Int> = listOf(
        R.string.tips_dialog_placeholder_1,
        R.string.tips_dialog_placeholder_2,
        R.string.tips_dialog_placeholder_3,
        R.string.tips_dialog_placeholder_4
    )
    override val dialogFaceButtons =
        createTipsDialogButtons(source, dialogController)
}
