package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.features.build.tips.MotorTipsDialogFace
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

sealed class MotorTestDialog(source: Source) : TestDialog() {

    override val dialogFaces: List<DialogFace<*>> = listOf(
        MotorTestDialogFace(this),
        MotorTipsDialogFace(source, this)
    )

    inner class MotorTestDialogFace(dialog: RoboticsDialog) : TestDialogFace(dialog) {
        override val imageResource: Int = R.drawable.test_motor
        override val textResource: Int = R.string.testing_motor_test

        override fun showTipsFace() {
            activateFace(dialogFaces.first { it is MotorTipsDialogFace })
        }
    }

    class Build : MotorTestDialog(Source.BUILD)
    class Configure : MotorTestDialog(Source.CONFIGURE)
}
