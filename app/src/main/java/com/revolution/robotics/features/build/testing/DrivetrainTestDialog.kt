package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.views.dialogs.DialogFace

class DrivetrainTestDialog : TestDialog() {

    companion object {
        fun newInstance() = DrivetrainTestDialog()
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(DrivetrainTestDialogFace())

    class DrivetrainTestDialogFace : TestDialogFace() {
        override val imageResource: Int = R.drawable.test_drivetrain
        override val textResource: Int = R.string.testing_drivetrain_test
    }
}
