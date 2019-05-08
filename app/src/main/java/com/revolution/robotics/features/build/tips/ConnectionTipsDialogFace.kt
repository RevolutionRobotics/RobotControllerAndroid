package com.revolution.robotics.features.build.tips

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog

class ConnectionTipsDialogFace(dialog: RoboticsDialog) : TipsDialogFace(dialog) {

    override val bulletCharacter: Char = '-'
    override val tipsList: List<Int> = listOf(
        R.string.tips_dialog_placeholder_1,
        R.string.tips_dialog_placeholder_2,
        R.string.tips_dialog_placeholder_3,
        R.string.tips_dialog_placeholder_4
    )
    override val dialogFaceButtons = listOf(
        DialogButton(R.string.tips_dialog_button_community, R.drawable.ic_community) {
            // TODO open community
        },
        DialogButton(R.string.connection_failed_try_again_button_title, R.drawable.ic_retry, true) {
            dialog.dismiss()
            dialog.dialogEventBus.publish(DialogEvent.ROBOT_RECONNECT)
        }
    )
}
