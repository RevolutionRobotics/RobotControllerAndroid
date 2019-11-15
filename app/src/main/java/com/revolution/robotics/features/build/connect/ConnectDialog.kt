package com.revolution.robotics.features.build.connect

import com.revolution.robotics.features.build.connect.availableRobotsFace.ConnectDialogFace
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog

class ConnectDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = ConnectDialog()
    }

    override val hasCloseButton = true
    override val dialogFaces = listOf(ConnectDialogFace(this))
    override val dialogButtons = emptyList<DialogButton>()
}
