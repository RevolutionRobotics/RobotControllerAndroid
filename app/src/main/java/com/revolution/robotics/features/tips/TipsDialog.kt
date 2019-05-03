package com.revolution.robotics.features.tips

import com.revolution.robotics.R
import com.revolution.robotics.features.tips.face.SensorTipsDialogFace
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class TipsDialog : RoboticsDialog() {
    override val hasCloseButton: Boolean = false
    override val dialogFaces: List<DialogFace<*>> = listOf(SensorTipsDialogFace())
    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(
            icon = R.drawable.ic_reconfig,
            text = R.string.tips_dialog_button_reconfigure,
            isHighlighted = false,
            onClick = { dismiss() }
        ),
        DialogButton(
            icon = R.drawable.ic_community,
            text = R.string.tips_dialog_button_community,
            isHighlighted = false,
            onClick = { dismiss() }
        ),
        DialogButton(
            icon = R.drawable.ic_retry,
            text = R.string.tips_dialog_button_try_again,
            isHighlighted = true,
            onClick = { dismiss() }
        )
    )
}
