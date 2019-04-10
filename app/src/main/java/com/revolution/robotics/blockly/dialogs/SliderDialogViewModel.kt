package com.revolution.robotics.blockly.dialogs

import android.webkit.JsPromptResult
import android.widget.SeekBar
import com.revolution.robotics.databinding.BlocklyDialogSliderBinding

class SliderDialogViewModel(jsResult: JsPromptResult, binding: BlocklyDialogSliderBinding) :
    BlocklyDialogViewModel<BlocklyDialogSliderBinding>(jsResult, binding), SeekBar.OnSeekBarChangeListener {

    var minValue: Int? = null
    var maxValue: Int? = null
        get() = (field ?: 0) - (minValue ?: 0)

    init {
        binding.slider.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        binding.label.text = (progress + (minValue ?: 0)).toString()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

    override fun getResult() = "${binding.slider.progress + (minValue ?: 0)}"
}
