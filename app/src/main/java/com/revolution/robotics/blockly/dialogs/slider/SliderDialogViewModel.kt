package com.revolution.robotics.blockly.dialogs.slider

import android.widget.SeekBar
import androidx.databinding.ObservableField
import org.revolution.blockly.view.DialogFactory

class SliderDialogViewModel(options: DialogFactory.SliderOptions) : SeekBar.OnSeekBarChangeListener {

    val minValue = options.minValue
    val maxValue = options.maxValue
        get() = field - minValue
    val labelText = ObservableField<String>("$minValue")

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        labelText.set((progress + minValue).toString())
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
}
