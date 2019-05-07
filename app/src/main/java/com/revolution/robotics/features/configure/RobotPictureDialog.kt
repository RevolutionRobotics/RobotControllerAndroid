package com.revolution.robotics.features.configure

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogRobotPictureBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class RobotPictureDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = RobotPictureDialog()
    }

    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(
        RobotImageDialogFace()
    )
    override val dialogButtons = listOf(
        DialogButton(R.string.camera_dialog_delete_title, R.drawable.ic_delete) {
            dialog.dismiss()
        },
        DialogButton(R.string.camera_dialog_new_photo_title, R.drawable.ic_camera, true) {
            dialog.dismiss()
        }
    )

    class RobotImageDialogFace : DialogFace<DialogRobotPictureBinding>(R.layout.dialog_robot_picture)
}
