package com.revolution.robotics.features.myRobots.info

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogDeleteRobotBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class DeleteRobotDialogFace(dialog: RoboticsDialog) :
    DialogFace<DialogDeleteRobotBinding>(R.layout.dialog_delete_robot, dialog) {
    override val dialogFaceButtons: MutableList<DialogButton> =
        mutableListOf(DialogButton(R.string.cancel, R.drawable.ic_close) {
            dialog.dismiss()
        },
            DialogButton(R.string.delete_robot_confirm, R.drawable.ic_delete, true) {
                dialog.dismiss()
                dialog.dialogEventBus.publish(DialogEvent.DELETE_ROBOT.apply {
                    extras.putParcelable(
                        InfoRobotDialog.KEY_ROBOT,
                        dialog.arguments?.getParcelable(InfoRobotDialog.KEY_ROBOT)
                    )
                })
            })
}