package com.revolution.robotics.features.controllers.delete

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogDeleteRobotBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class DeleteControllerDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = DeleteControllerDialog()
    }

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        DeleteControllerDialogFace()
    )
    override val dialogButtons = listOf(
        DialogButton(R.string.delete_controller_cancel, R.drawable.ic_close) {
            dismissAllowingStateLoss()
        },
        DialogButton(R.string.delete_controller_confirm, R.drawable.ic_delete, true) {
            dismissAllowingStateLoss()
            dialogEventBus.publish(DialogEvent.DELETE_CONTROLLER)
        }
    )

    class DeleteControllerDialogFace : DialogFace<DialogDeleteRobotBinding>(R.layout.dialog_delete_controller)
}
