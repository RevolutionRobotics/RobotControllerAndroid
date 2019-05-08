package com.revolution.robotics.features.myRobots.delete

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogDeleteRobotBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class DeleteRobotDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = DeleteRobotDialog()
    }

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        DeleteRobotDialogFace()
    )
    override val dialogButtons = listOf(
        DialogButton(R.string.cancel, R.drawable.ic_close) {
            dialog.dismiss()
        },
        DialogButton(R.string.delete_robot_confirm, R.drawable.ic_delete, true) {
            dialogEventBus.publish(DialogEvent.DELETE_ROBOT)
            dialog.dismiss()
        }
    )

    class DeleteRobotDialogFace : DialogFace<DialogDeleteRobotBinding>(R.layout.dialog_delete_robot)
}
