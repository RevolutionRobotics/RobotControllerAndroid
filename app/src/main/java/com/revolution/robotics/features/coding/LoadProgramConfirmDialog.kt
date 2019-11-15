package com.revolution.robotics.features.coding

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogConfirmBinding
import com.revolution.robotics.utils.ConfirmDialog

class LoadProgramConfirmDialog : ConfirmDialog(
    positiveButtonText = R.string.dialog_load_program_confirm_button_positive,
    negativeButtonText = R.string.dialog_load_program_confirm_button_negative
) {

    companion object {
        fun newInstance() = LoadProgramConfirmDialog()
    }

    override fun onActivated(binding: DialogConfirmBinding?) {
        binding?.apply {
            confirmTitle.setText(R.string.dialog_load_program_confirm_title)
        }
    }

    override fun onCancelled() {
        dialogEventBus.publish(DialogEvent.PROGRAM_CONFIRM_LOAD_WITHOUT_SAVE)
    }

    override fun onConfirmed() {
        dialogEventBus.publish(DialogEvent.PROGRAM_CONFIRM_LOAD_WITH_SAVE)
    }
}
