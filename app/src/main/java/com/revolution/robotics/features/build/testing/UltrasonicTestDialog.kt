package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.features.build.tips.TipsDialogFace
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class UltrasonicTestDialog : TestDialog() {

    override val dialogFaces: List<DialogFace<*>> = listOf(
        UltrasonicTestDialogFace(this),
        TipsDialogFace(Source.CONFIGURE, this, this)
    )

    inner class UltrasonicTestDialogFace(dialog: RoboticsDialog) : TestDialogFace(dialog) {
        override val imageResource: Int = R.drawable.test_ultrasonic
        override val textResource: Int = R.string.testing_ultrasonic_test

        override fun showTipsFace() {
            activateFace(dialogFaces.first { it is TipsDialogFace })
        }
    }
}
