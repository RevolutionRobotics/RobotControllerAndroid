package com.revolution.robotics.build.milestone

import com.revolution.robotics.R
import com.revolution.robotics.core.dialogs.DialogButton
import com.revolution.robotics.core.dialogs.RoboticsDialog
import com.revolution.robotics.databinding.DialogFaceMilestoneFinishedBinding

class MilestoneFinishedDialog : RoboticsDialog() {
    override val dialogFaces: List<DialogFace<*>> = listOf(MilestoneFinishedDialgoFace())
    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(
            R.string.build_chapter_finish_dialog_button_home,
            R.drawable.ic_home,
            false
        ) {
            requireActivity().onBackPressed()
        },
        DialogButton(
            R.string.build_chapter_finish_dialog_button_next_chapter,
            R.drawable.ic_next,
            false
        ) {
        },
        DialogButton(
            R.string.build_chapter_finish_dialog_button_test,
            R.drawable.ic_test,
            true
        ) {
        }
    )

    class MilestoneFinishedDialgoFace :
        DialogFace<DialogFaceMilestoneFinishedBinding>(R.layout.dialog_face_milestone_finished)
}