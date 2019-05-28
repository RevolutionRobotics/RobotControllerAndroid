package com.revolution.robotics.blockly.dialogs.soundPicker.adapter

import com.revolution.robotics.blockly.dialogs.soundPicker.SoundOption
import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class SoundPickerAdapter : RecyclerAdapter<SoundOption>() {

    init {
        adapterDelegate.register(SoundOptionDelegateItem())
    }

}