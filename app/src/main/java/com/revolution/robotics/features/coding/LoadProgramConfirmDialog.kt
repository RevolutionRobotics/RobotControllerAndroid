package com.revolution.robotics.features.coding

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogConfirmBinding
import com.revolution.robotics.utils.ConfirmDialog

class LoadProgramConfirmDialog : ConfirmDialog(R.string.dialog_load_program_confirm_button_positive) {

    companion object {
        fun newInstance() = LoadProgramConfirmDialog()
    }

    override fun onActivated(binding: DialogConfirmBinding?) {
        binding?.apply {
            confirmTitle.setText(R.string.dialog_load_program_confirm_title)
            confirmDescription.setText(R.string.dialog_load_program_confirm_description)
        }
    }

    override fun onConfirmed() {
        dialogEventBus.publish(DialogEvent.PROGRAM_CONFIRM_LOAD)
    }
}
