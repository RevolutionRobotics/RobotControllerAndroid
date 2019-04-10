package com.revolution.robotics.blockly.dialogs

import android.webkit.JsPromptResult
import com.revolution.robotics.databinding.BlocklyDialogInputTextBinding

class TextInputDialogViewModel(jsResult: JsPromptResult, binding: BlocklyDialogInputTextBinding) :
    BlocklyDialogViewModel<BlocklyDialogInputTextBinding>(jsResult, binding) {

    override fun getResult() = "${binding.input.text}"
}
