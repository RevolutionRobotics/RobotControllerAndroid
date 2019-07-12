package com.revolution.robotics.features.coding

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogConfirmBinding
import com.revolution.robotics.utils.ConfirmDialog

class LeaveProgramDialog : ConfirmDialog(
    positiveButtonText = R.string.program_confirm_positive,
    negativeButtonText = R.string.program_leave_confirmation_dialog_leave_button
) {

    companion object {
        fun newInstance() = LeaveProgramDialog()
    }

    override fun onActivated(binding: DialogConfirmBinding?) {
        binding?.apply {
            confirmTitle.setText(R.string.program_confirm_title)
        }
    }

    override fun onCancelled() {
        dialogEventBus.publish(DialogEvent.PROGRAM_CONFIRM_CLOSE_WITHOUT_SAVE)
    }

    override fun onConfirmed() {
        dialogEventBus.publish(DialogEvent.PROGRAM_CONFIRM_CLOSE_WITH_SAVE)
    }
}
