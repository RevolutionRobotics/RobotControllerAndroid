package com.revolution.robotics.features.testing.face

import com.revolution.robotics.R
import com.revolution.robotics.features.testing.face.base.TestingDialogFace

class SensorTestDialogFace : TestingDialogFace() {
    override val imageResource: Int = R.drawable.sensor_test
    override val textResource: Int = R.string.build_robot_testing_sensor_test_text
}
