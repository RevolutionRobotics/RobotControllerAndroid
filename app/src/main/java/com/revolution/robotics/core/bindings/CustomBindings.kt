package com.revolution.robotics.core.bindings

import android.view.View
import android.widget.SeekBar
import androidx.databinding.BindingAdapter
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxConfig
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxDrawable

@BindingAdapter("listener")
fun setSeekbarListener(seekBar: SeekBar, listener: SeekBar.OnSeekBarChangeListener) {
    seekBar.setOnSeekBarChangeListener(listener)
}

@BindingAdapter("chippedBoxConfig")
fun setChippedBoxConfig(view: View, config: ChippedBoxConfig) {
    view.background = ChippedBoxDrawable(view.context, config)
}
