package com.revolution.robotics.blockly.dialogs.colorPicker.adapter

import com.revolution.robotics.blockly.dialogs.colorPicker.ColorOption
import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class ColorPickerAdapter : RecyclerAdapter<ColorOption>() {

    init {
        adapterDelegate.register(ColorOptionDelegateItem())
    }

}