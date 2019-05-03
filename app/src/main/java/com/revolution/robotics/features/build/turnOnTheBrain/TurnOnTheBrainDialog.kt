package com.revolution.robotics.features.build.turnOnTheBrain

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.eventBus.dialog.DialogId
import com.revolution.robotics.databinding.DialogTurnOnTheBrainBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class TurnOnTheBrainDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = TurnOnTheBrainDialog()
    }

    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(
        TurnOnTheBrainDialogFace()
    )

    override val dialogButtons = listOf(
        DialogButton(R.string.build_robot_later, R.drawable.ic_clock) {
            dialog.dismiss()
            dialogEventBus.publish(DialogId.TURN_ON_THE_BRAIN, DialogEventBus.Event.NEGATIVE)
        },
        DialogButton(R.string.build_robot_tips, R.drawable.ic_tips) {
            // TODO show tips
        },
        DialogButton(R.string.build_robot_start, R.drawable.ic_play, true) {
            dialog.dismiss()
            dialogEventBus.publish(DialogId.TURN_ON_THE_BRAIN, DialogEventBus.Event.POSITIVE)
        }
    )

    override fun onDialogCloseButtonClicked() {
        dialogEventBus.publish(DialogId.TURN_ON_THE_BRAIN, DialogEventBus.Event.NEGATIVE)
    }

    class TurnOnTheBrainDialogFace : DialogFace<DialogTurnOnTheBrainBinding>(R.layout.dialog_turn_on_the_brain)
}
