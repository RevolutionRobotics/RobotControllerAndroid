package com.revolution.robotics.features.coding.programs.adapter

import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class RobotsAdapter : RecyclerAdapter<RobotViewModel>() {

    init {
        adapterDelegate.register(RobotSelectorDelegateItem())
    }
}
