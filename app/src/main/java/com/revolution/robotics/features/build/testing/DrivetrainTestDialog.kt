package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.features.build.tips.TipsDialogFace
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class DrivetrainTestDialog : TestDialog() {

    override val dialogFaces: List<DialogFace<*>> = listOf(
        DrivetrainTestDialogFace(this),
        TipsDialogFace(Source.CONFIGURE, this, this)
    )

    inner class DrivetrainTestDialogFace(dialog: RoboticsDialog) : TestDialogFace(dialog) {
        override val imageResource: Int = R.drawable.test_drivetrain
        override val textResource: Int = R.string.testing_drivetrain_test

        override fun showTipsFace() {
            activateFace(dialogFaces.first { it is TipsDialogFace })
        }
    }
}
