package com.revolution.robotics.features.controllers.programSelector.adapter

import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class ProgramSelectorAdapter : RecyclerAdapter<ProgramViewModel>() {

    init {
        adapterDelegate.register(ProgramSelectorDelegateItem())
    }
}
