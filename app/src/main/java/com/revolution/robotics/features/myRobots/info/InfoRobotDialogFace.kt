package com.revolution.robotics.features.myRobots.info

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.formatYearMonthDayDotted
import com.revolution.robotics.databinding.DialogRobotInfoBinding
import com.revolution.robotics.features.myRobots.info.InfoRobotDialog.Companion.KEY_ROBOT
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class InfoRobotDialogFace(dialog: RoboticsDialog, showEditButton: Boolean) :
    DialogFace<DialogRobotInfoBinding>(R.layout.dialog_robot_info, dialog) {

    override val dialogFaceButtons: MutableList<DialogButton> = mutableListOf(
        DialogButton(R.string.info_robot_delete, R.drawable.ic_delete) {
            (dialog as? InfoRobotDialog)?.activateDeleteFace()
        },
        DialogButton(R.string.info_robot_duplicate, R.drawable.ic_copy) {
            dialog.dismiss()
            dialog.dialogEventBus.publish(DialogEvent.DUPLICATE_ROBOT.apply {
                extras.putParcelable(KEY_ROBOT, dialog.arguments?.getParcelable<UserRobot>(KEY_ROBOT))
            })
        }).apply {
        if (showEditButton) {
            add(DialogButton(R.string.info_robot_edit, R.drawable.ic_edit, true) {
                dialog.dismiss()
                dialog.dialogEventBus.publish(DialogEvent.EDIT_ROBOT.apply {
                    extras.putParcelable(KEY_ROBOT, dialog.arguments?.getParcelable<UserRobot>(KEY_ROBOT))
                })
            })
        }
    }

    override fun onActivated() {
        super.onActivated()
        (dialog as? InfoRobotDialog)?.apply {
            arguments?.getParcelable<UserRobot>(KEY_ROBOT)?.let { userRobot ->
                binding?.viewModel = InfoRobotDialogViewModel(
                    robotName = userRobot.name ?: "",
                    robotDescription = userRobot.description ?: "",
                    dateText = userRobot.lastModified?.formatYearMonthDayDotted() ?: "",
                    errorTextVisible = userRobot.isCustomBuild()
                )
            }
        }
    }
}
