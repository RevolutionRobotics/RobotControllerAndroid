package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.views.dialogs.DialogFace

class MovementTestDialog : TestDialog() {

    companion object {
        fun newInstance() = MovementTestDialog()
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(
        MovementTestDialogFace()
    )

    class MovementTestDialogFace : TestDialogFace() {
        override val imageResource: Int = R.drawable.movement_test
        override val textResource: Int = R.string.build_robot_testing_movement_test_text
    }
}
