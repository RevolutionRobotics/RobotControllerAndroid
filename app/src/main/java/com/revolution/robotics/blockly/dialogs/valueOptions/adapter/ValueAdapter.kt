package com.revolution.robotics.blockly.dialogs.valueOptions.adapter

import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class ValueAdapter : RecyclerAdapter<ValueViewModel>() {

    init {
        adapterDelegate.register(ValueDelegateItem())
    }
}
