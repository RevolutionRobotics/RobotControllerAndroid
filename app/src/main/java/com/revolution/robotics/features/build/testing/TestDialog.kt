package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog

abstract class TestDialog : RoboticsDialog() {

    override val hasCloseButton = true
    override val dialogButtons = listOf(
        DialogButton(
            text = R.string.build_robot_testing_negative_button_title,
            icon = R.drawable.ic_close,
            isHighlighted = false,
            onClick = { dismiss() }
        ),
        DialogButton(
            text = R.string.build_robot_testing_positive_button_title,
            icon = R.drawable.ic_check,
            isHighlighted = true,
            onClick = { dismiss() }
        )
    )
}
