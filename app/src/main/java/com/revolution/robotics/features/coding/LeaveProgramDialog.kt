package com.revolution.robotics.features.coding

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogConfirmBinding
import com.revolution.robotics.utils.ConfirmDialog

class LeaveProgramDialog : ConfirmDialog(R.string.program_confirm_positive) {

    companion object {
        fun newInstance() = LeaveProgramDialog()
    }

    override fun onActivated(binding: DialogConfirmBinding?) {
        binding?.apply {
            confirmTitle.setText(R.string.program_confirm_title)
            confirmDescription.setText(R.string.program_confirm_description)
        }
    }

    override fun onConfirmed() {
        dialogEventBus.publish(DialogEvent.PROGRAM_CONFIRM_CLOSE)
    }
}
