package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.views.dialogs.DialogFace

class SensorTestDialog : TestDialog() {

    companion object {
        fun newInstance() = SensorTestDialog()
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(
        SensorTestDialogFace()
    )

    class SensorTestDialogFace : TestDialogFace() {
        override val imageResource: Int = R.drawable.sensor_test
        override val textResource: Int = R.string.build_robot_testing_sensor_test_text
    }
}

