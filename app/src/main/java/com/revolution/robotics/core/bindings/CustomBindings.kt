package com.revolution.robotics.core.bindings

import android.widget.SeekBar
import androidx.databinding.BindingAdapter

@BindingAdapter("listener")
fun setSeekbarListener(seekBar: SeekBar, listener: SeekBar.OnSeekBarChangeListener) {
    seekBar.setOnSeekBarChangeListener(listener)
}
