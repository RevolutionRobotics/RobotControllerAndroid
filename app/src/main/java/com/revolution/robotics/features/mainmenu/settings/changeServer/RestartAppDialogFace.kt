package com.revolution.robotics.features.mainmenu.settings.changeServer

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogChangeServerRestartBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace

class RestartAppDialogFace(dialog: ChangeServerDialog) :
    DialogFace<DialogChangeServerRestartBinding>(R.layout.dialog_change_server_restart, dialog) {

    override val dialogFaceButtons: MutableList<DialogButton> = mutableListOf(
         DialogButton(R.string.no, R.drawable.ic_close) {
            dialog.dismissAllowingStateLoss()
        },
        DialogButton(R.string.change_server_restart_btn, R.drawable.ic_check, true) {
            dialog.dismissAllowingStateLoss()
            dialog.dialogEventBus.publish(DialogEvent.QUIT_APP)
        })

}
