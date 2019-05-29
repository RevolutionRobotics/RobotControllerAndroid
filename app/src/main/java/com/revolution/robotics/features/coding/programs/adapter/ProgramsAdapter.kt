package com.revolution.robotics.features.coding.programs.adapter

import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class ProgramsAdapter : RecyclerAdapter<ProgramViewModel>() {

    init {
        adapterDelegate.register(ProgramsDelegateItem())
    }
}
