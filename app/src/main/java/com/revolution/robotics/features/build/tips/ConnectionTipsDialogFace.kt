package com.revolution.robotics.features.build.tips

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.openUrl
import com.revolution.robotics.features.build.testing.TestDialog
import com.revolution.robotics.features.community.CommunityFragment
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog

class ConnectionTipsDialogFace(dialog: RoboticsDialog) : TipsDialogFace(TestDialog.Source.CONFIGURE, null, dialog) {

    override val bulletCharacter: Char = '-'
    override val tipsList: List<Int> = listOf(R.string.tips_dialog_placeholder_1)

    override val dialogFaceButtons = mutableListOf(
        DialogButton(R.string.tips_dialog_button_community, R.drawable.ic_community) {
            dialog.requireActivity().openUrl(CommunityFragment.COMMUNITY_FORUMS_URL)
        },
        DialogButton(R.string.connection_failed_try_again_button_title, R.drawable.ic_retry, true) {
            dialog.dismissAllowingStateLoss()
            dialog.dialogEventBus.publish(DialogEvent.ROBOT_RECONNECT)
        }
    )
}
