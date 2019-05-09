package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.features.build.tips.DrivetrainTipsDialogFace
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

sealed class DrivetrainTestDialog(source: Source) : TestDialog() {

    override val dialogFaces: List<DialogFace<*>> = listOf(
        DrivetrainTestDialogFace(this),
        DrivetrainTipsDialogFace(source, this)
    )

    inner class DrivetrainTestDialogFace(dialog: RoboticsDialog) : TestDialogFace(dialog) {
        override val imageResource: Int = R.drawable.test_drivetrain
        override val textResource: Int = R.string.testing_drivetrain_test

        override fun showTipsFace() {
            activateFace(dialogFaces.first { it is DrivetrainTipsDialogFace })
        }
    }

    class Build : DrivetrainTestDialog(Source.BUILD)
    class Configure : DrivetrainTestDialog(Source.CONFIGURE)
}
