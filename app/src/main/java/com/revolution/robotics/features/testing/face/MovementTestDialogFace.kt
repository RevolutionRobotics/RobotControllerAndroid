package com.revolution.robotics.features.testing.face

import com.revolution.robotics.R
import com.revolution.robotics.features.testing.face.base.TestingDialogFace

class MovementTestDialogFace : TestingDialogFace() {
    override val imageResource: Int = R.drawable.movement_test
    override val textResource: Int = R.string.build_robot_testing_movement_test_text
}
