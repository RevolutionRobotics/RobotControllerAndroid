package com.revolution.robotics.features.build.turnOnTheBrain

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogTurnOnTheBrainBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class TurnOnTheBrainDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = TurnOnTheBrainDialog()
    }

    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(TurnOnTheBrainDialogFace())
    override val dialogButtons = listOf(
        DialogButton(R.string.build_robot_later, R.drawable.ic_clock) {
            dialog.dismiss()
        },
        DialogButton(R.string.build_robot_tips, R.drawable.ic_tips) {
            // TODO show tips
        },
        DialogButton(R.string.build_robot_start, R.drawable.ic_play, true) {
            dialog.dismiss()
        }
    )

    class TurnOnTheBrainDialogFace : DialogFace<DialogTurnOnTheBrainBinding>(R.layout.dialog_turn_on_the_brain)
}
