package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.features.build.tips.UltrasonicTipsDialogFace
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

sealed class UltrasonicTestDialog(source: Source) : TestDialog() {

    override val dialogFaces: List<DialogFace<*>> = listOf(
        UltrasonicTestDialogFace(this),
        UltrasonicTipsDialogFace(source, this)
    )

    inner class UltrasonicTestDialogFace(dialog: RoboticsDialog) : TestDialogFace(dialog) {
        override val imageResource: Int = R.drawable.test_ultrasonic
        override val textResource: Int = R.string.testing_ultrasonic_test

        override fun showTipsFace() {
            activateFace(dialogFaces.first { it is UltrasonicTipsDialogFace })
        }
    }

    class Build : UltrasonicTestDialog(Source.BUILD)
    class Configure : UltrasonicTestDialog(Source.CONFIGURE)
}
