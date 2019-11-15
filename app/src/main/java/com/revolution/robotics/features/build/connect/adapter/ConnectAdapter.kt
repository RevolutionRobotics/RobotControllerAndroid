package com.revolution.robotics.features.build.connect.adapter

import androidx.lifecycle.LifecycleOwner
import com.revolution.robotics.core.utils.recyclerview.DiffUtilRecyclerAdapter

class ConnectAdapter(lifecycleOwner: LifecycleOwner) : DiffUtilRecyclerAdapter<ConnectRobotItem>() {

    init {
        adapterDelegate.register(ConnectRobotDelegateItem(lifecycleOwner))
    }
}
