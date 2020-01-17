package com.revolution.robotics.blockly.dialogs.lightEffectPicker.adapter

import com.revolution.robotics.blockly.dialogs.lightEffectPicker.LightEffectOption
import com.revolution.robotics.blockly.dialogs.soundPicker.SoundOption
import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class LightEffectPickerAdapter : RecyclerAdapter<LightEffectOption>() {

    init {
        adapterDelegate.register(LightEffectOptionDelegateItem())
    }
}
