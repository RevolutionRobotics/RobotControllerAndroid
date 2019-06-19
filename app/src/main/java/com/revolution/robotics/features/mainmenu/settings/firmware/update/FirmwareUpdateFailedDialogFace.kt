package com.revolution.robotics.features.mainmenu.settings.firmware.update

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogFirmwareUpdateFailedBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace

class FirmwareUpdateFailedDialogFace(dialog: FirmwareUpdateDialog) : DialogFace<DialogFirmwareUpdateFailedBinding>(
    R.layout.dialog_firmware_update_failed,
    dialog
) {

    override val dialogFaceButtons: MutableList<DialogButton> = mutableListOf(
        DialogButton(R.string.firmware_update_failed_update_later_button, R.drawable.ic_skip) {
            dialog.dismissAllowingStateLoss()
        },
        DialogButton(R.string.firmware_update_failed_update_community_button, R.drawable.ic_tips, true) {
            dialog.navigator.navigate(R.id.toCommunity)
        },
        DialogButton(R.string.firmware_update_failed_update_try_again_button, R.drawable.ic_retry, true) {
            dialog.retryFirmwareUpload()
        }
    )
}
