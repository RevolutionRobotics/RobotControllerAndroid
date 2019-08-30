package com.revolution.robotics.features.build.connect

import com.revolution.robotics.features.build.connect.availableRobotsFace.ConnectDialogFace
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog

class ConnectDialog : RoboticsDialog() {


    companion object {
        fun newInstance(autoConnectDeviceName: String? = null) : ConnectDialog{
            this.autoConnectDeviceName = autoConnectDeviceName
            return ConnectDialog()
        }
        var autoConnectDeviceName: String? = null
    }

    override val hasCloseButton = true
    override val dialogFaces = listOf(ConnectDialogFace(this))
    override val dialogButtons = emptyList<DialogButton>()
}
