package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.views.dialogs.DialogFace

class UltrasonicTestDialog : TestDialog() {

    companion object {
        fun newInstance() = UltrasonicTestDialog()
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(UltrasonicTestDialogFace())

    class UltrasonicTestDialogFace : TestDialogFace() {
        override val imageResource: Int = R.drawable.test_ultrasonic
        override val textResource: Int = R.string.testing_ultrasonic_test
    }
}
