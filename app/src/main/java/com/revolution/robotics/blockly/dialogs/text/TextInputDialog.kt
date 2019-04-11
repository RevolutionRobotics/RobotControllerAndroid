package com.revolution.robotics.blockly.dialogs.text

import com.revolution.robotics.BaseDialogFragment
import com.revolution.robotics.R
import com.revolution.robotics.databinding.BlocklyDialogInputTextBinding

class TextInputDialog : BaseDialogFragment<BlocklyDialogInputTextBinding>(R.layout.blockly_dialog_input_text) {

    override fun getResult() = "${binding.input.text}"
}
