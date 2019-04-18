package com.revolution.robotics.features.availableRobots.adapter

import androidx.lifecycle.LifecycleOwner
import com.revolution.robotics.core.utils.recyclerview.DiffUtilRecyclerAdapter
import com.revolution.robotics.features.availableRobots.availableRobotsFace.AvailableRobotsDialogFaceViewModel

class AvailableRobotsAdapter(lifecycleOwner: LifecycleOwner) :
    DiffUtilRecyclerAdapter<AvailableRobotsItem>() {
    init {
        adapterDelegate.register(AvailableRobotsAdapterDelegateItem(lifecycleOwner))
    }
}
