package com.revolution.robotics.blockly.dialogs.variableOptions.adapter

import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class VariableAdapters : RecyclerAdapter<VariableViewModel>() {

    init {
        adapterDelegate.register(VariableDelegateItem())
    }
}
