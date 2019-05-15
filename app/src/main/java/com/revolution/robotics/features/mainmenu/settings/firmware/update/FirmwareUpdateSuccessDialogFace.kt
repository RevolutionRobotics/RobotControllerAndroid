package com.revolution.robotics.features.mainmenu.settings.firmware.update

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogConnectionSuccessBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class FirmwareUpdateSuccessDialogFace(dialog: RoboticsDialog) :
    DialogFace<DialogConnectionSuccessBinding>(R.layout.dialog_firmware_update_success, dialog) {

    override val dialogFaceButtons =
        mutableListOf(DialogButton(R.string.firmware_done_button, R.drawable.ic_check, true) {
            dialog.dismissAllowingStateLoss()
        })
}
