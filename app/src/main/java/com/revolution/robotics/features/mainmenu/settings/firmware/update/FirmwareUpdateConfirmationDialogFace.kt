package com.revolution.robotics.features.mainmenu.settings.firmware.update

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogFirmwareUpdateConfirmationBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace

class FirmwareUpdateConfirmationDialogFace(private val firmwareUpdateDialog: FirmwareUpdateDialog) :
    DialogFace<DialogFirmwareUpdateConfirmationBinding>(
        R.layout.dialog_firmware_update_confirmation, firmwareUpdateDialog
    ) {

    override val dialogFaceButtons: MutableList<DialogButton> =
        mutableListOf(DialogButton(R.string.cancel, R.drawable.ic_close, false) {
            firmwareUpdateDialog.activateLoadingFace()
        },
            DialogButton(R.string.framework_confirmation_dialog_stop_button, R.drawable.ic_retry, true) {
                firmwareUpdateDialog.stopFrameworkUpdate()
            })
}