package com.revolution.robotics.features.availableRobots

import com.revolution.robotics.features.availableRobots.availableRobotsFace.AvailableRobotsDialogFace
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class AvailableRobotsDialog : RoboticsDialog() {
    override val hasCloseButton: Boolean = true

    override val dialogFaces: List<DialogFace<*>> =
        listOf(AvailableRobotsDialogFace())
    override val dialogButtons: List<DialogButton> = emptyList()
}
