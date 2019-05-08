package com.revolution.robotics.features.build.connectionResult

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogConnectionFailedBinding
import com.revolution.robotics.features.build.tips.ConnectionTipsDialogFace
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class ConnectionFailedDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = ConnectionFailedDialog()
    }

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        ConnectionFailedDialogFace(this),
        ConnectionTipsDialogFace(this)
    )
    override val dialogButtons = listOf(
        DialogButton(R.string.connection_failed_skip_button_title, R.drawable.ic_skip) {
            dialog.dismiss()
        }
    )

    class ConnectionFailedDialogFace(dialog: RoboticsDialog) :
        DialogFace<DialogConnectionFailedBinding>(R.layout.dialog_connection_failed, dialog) {
        override val dialogFaceButtons = listOf(
            DialogButton(R.string.connection_failed_tips_button_title, R.drawable.ic_tips, true) {
                dialog.activateFace(dialog.dialogFaces.first { it is ConnectionTipsDialogFace })
            },
            DialogButton(R.string.connection_failed_try_again_button_title, R.drawable.ic_retry, true) {
                dialog.dismiss()
                dialog.dialogEventBus.publish(DialogEvent.ROBOT_RECONNECT)
            }
        )
    }
}
