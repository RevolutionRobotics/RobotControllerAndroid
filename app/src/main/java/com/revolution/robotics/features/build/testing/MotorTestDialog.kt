package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.views.dialogs.DialogFace

class MotorTestDialog : TestDialog() {

    companion object {
        fun newInstance() = MotorTestDialog()
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(MotorTestDialogFace())

    class MotorTestDialogFace : TestDialogFace() {
        override val imageResource: Int = R.drawable.test_motor
        override val textResource: Int = R.string.testing_motor_test
    }
}
