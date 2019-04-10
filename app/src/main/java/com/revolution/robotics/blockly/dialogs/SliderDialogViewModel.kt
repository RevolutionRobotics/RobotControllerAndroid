package com.revolution.robotics.blockly.dialogs

import android.webkit.JsPromptResult
import com.revolution.robotics.databinding.BlocklyDialogSliderBinding

class SliderDialogViewModel(jsResult: JsPromptResult, binding: BlocklyDialogSliderBinding) :
    BlocklyDialogViewModel<BlocklyDialogSliderBinding>(jsResult, binding) {

    override fun getResult() = "${binding.slider.progress}"
}