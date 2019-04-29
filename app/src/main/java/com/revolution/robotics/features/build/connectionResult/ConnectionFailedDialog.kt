package com.revolution.robotics.features.build.connectionResult

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.eventBus.dialog.DialogId
import com.revolution.robotics.databinding.DialogConnectionFailedBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class ConnectionFailedDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = ConnectionFailedDialog()
    }

    private val dialogEventBus: DialogEventBus by kodein.instance()

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        ConnectionFailedDialogFace()
    )
    override val dialogButtons = listOf(
        DialogButton(R.string.connection_failed_skip_button_title, R.drawable.ic_skip) {
            dialog.dismiss()
        },
        DialogButton(R.string.connection_failed_tips_button_title, R.drawable.ic_tips, true) {
            dialog.dismiss()
            dialogEventBus.publish(DialogId.CONNECTION_FAILED, DialogEventBus.Event.OTHER)
        },
        DialogButton(R.string.connection_failed_try_again_button_title, R.drawable.ic_retry, true) {
            dialog.dismiss()
            dialogEventBus.publish(DialogId.CONNECTION_FAILED, DialogEventBus.Event.POSITIVE)
        }
    )

    class ConnectionFailedDialogFace : DialogFace<DialogConnectionFailedBinding>(R.layout.dialog_connection_failed)
}
