package com.revolution.robotics.features.build.buildFinished

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogBuildFinishedBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class BuildFinishedDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = BuildFinishedDialog()
    }

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        BuildFinishedDialogFace()
    )
    override val dialogButtons = listOf(
        DialogButton(R.string.build_chapter_finish_dialog_button_home, R.drawable.ic_home) {
            // TODO navigate user home
        },
        DialogButton(R.string.build_robot_finished_button, R.drawable.ic_play, true) {
            // TODO let's drive!
            dismissAllowingStateLoss()
        }
    )

    class BuildFinishedDialogFace : DialogFace<DialogBuildFinishedBinding>(R.layout.dialog_build_finished)
}
