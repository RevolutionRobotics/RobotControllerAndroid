package com.revolution.robotics.features.whoToBuild.delete

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogCannotDeleteRobotBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class CannotDeleteRobotDialog(
    private val robotName: String?
): RoboticsDialog() {

    override val hasCloseButton = true
    override val dialogFaces = listOf(CannotDeleteRobotDialogFace(this, robotName))
    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(R.string.ok, R.drawable.ic_check, true) {
            dismissAllowingStateLoss()
        }
    )

    class CannotDeleteRobotDialogFace(
        private val confirmDialog: CannotDeleteRobotDialog,
        private val robotName: String?) :
        DialogFace<DialogCannotDeleteRobotBinding>(R.layout.dialog_cannot_delete_robot, confirmDialog) {
        override fun onActivated() {
            super.onActivated()
            binding?.cannotDeleteRobotDescription?.text = dialog?.getString(R.string.who_to_build_cannot_delete_robot_description, robotName)
        }
    }
}