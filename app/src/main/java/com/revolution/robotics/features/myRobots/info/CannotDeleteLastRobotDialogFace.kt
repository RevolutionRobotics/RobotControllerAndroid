package com.revolution.robotics.features.myRobots.info

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogDeleteRobotBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class CannotDeleteLastRobotDialogFace(dialog: RoboticsDialog) :
    DialogFace<DialogDeleteRobotBinding>(R.layout.dialog_cannot_delete_last_robot, dialog) {
    override val dialogFaceButtons: MutableList<DialogButton> =
        mutableListOf(DialogButton(R.string.ok, R.drawable.ic_check) {
            dialog.dismissAllowingStateLoss()
        })
}