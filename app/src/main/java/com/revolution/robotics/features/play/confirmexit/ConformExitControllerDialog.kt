package com.revolution.robotics.features.play.confirmexit

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogDeleteRobotBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class ConformExitControllerDialog : RoboticsDialog() {

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        ConformExitControllerDialogFace(this)
    )

    override val dialogButtons = mutableListOf(
        DialogButton(R.string.play_screen_exit_button, R.drawable.ic_back) {
            dismissAllowingStateLoss()
            dialogEventBus.publish(DialogEvent.EXIT_CONTROLLER)
        },
        DialogButton(R.string.cancel, R.drawable.ic_close) {
            dismissAllowingStateLoss()
        })

    class ConformExitControllerDialogFace(dialog: RoboticsDialog) :
        DialogFace<DialogDeleteRobotBinding>(R.layout.dialog_exit_controller, dialog)
}