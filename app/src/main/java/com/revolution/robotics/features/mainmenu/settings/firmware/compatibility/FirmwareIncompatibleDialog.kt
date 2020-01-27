package com.revolution.robotics.features.mainmenu.settings.firmware.compatibility

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogFirmwareIncompatibleBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class FirmwareIncompatibleDialog : RoboticsDialog() {

    override val hasCloseButton: Boolean = false
    override val dialogFaces: List<DialogFace<*>> = listOf(FirmwareIncompatibleDialogFace(this))
    override val dialogButtons: MutableList<DialogButton> = mutableListOf(
        DialogButton(R.string.firmware_incompatible_update_button,
            R.drawable.ic_check, true) {
            dismissAllowingStateLoss()
            dialogEventBus.publish(DialogEvent.FIRMWARE_INCOMPATIBLE_UPDATE)
        },
        DialogButton(
            R.string.firmware_incompatible_not_now_button,
            R.drawable.ic_close,
            false
        ) {
            dismissAllowingStateLoss()
            dialogEventBus.publish(DialogEvent.FIRMWARE_INCOMPATIBLE_UPDATE_LATER)
        }
    )

    class FirmwareIncompatibleDialogFace(roboticsDialog: RoboticsDialog) :
        DialogFace<DialogFirmwareIncompatibleBinding>(
            R.layout.dialog_firmware_incompatible,
            roboticsDialog
        )
}