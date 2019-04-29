package com.revolution.robotics.features.build.connectionResult

import androidx.core.view.postDelayed
import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogConnectionSuccessBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class ConnectionSuccessDialog : RoboticsDialog() {

    companion object {
        private const val DELAY_DISMISS_MS = 2500L

        fun newInstance() = ConnectionSuccessDialog()
    }

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        ConnectionSuccessDialogFace(this)
    )
    override val dialogButtons = emptyList<DialogButton>()

    class ConnectionSuccessDialogFace(dialog: RoboticsDialog) :
        DialogFace<DialogConnectionSuccessBinding>(R.layout.dialog_connection_success, dialog) {

        override fun onActivated() {
            binding?.root?.postDelayed(DELAY_DISMISS_MS) {
                dialog?.dismissAllowingStateLoss()
            }
        }
    }
}
