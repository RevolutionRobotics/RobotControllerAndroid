package com.revolution.robotics.features.controllers.buttonless.adapter

import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class ButtonlessProgramAdapter : RecyclerAdapter<ButtonlessProgramViewModel>() {

    init {
        adapterDelegate.register(ButtonlessProgramDelegateItem())
    }
}