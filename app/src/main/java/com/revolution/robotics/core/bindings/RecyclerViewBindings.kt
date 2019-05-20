package com.revolution.robotics.core.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.features.build.connect.adapter.ConnectAdapter
import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem
import com.revolution.robotics.features.configure.controller.program.priority.ProgramPriorityAdapter
import com.revolution.robotics.features.configure.controller.program.priority.ProgramPriorityItemViewModel

@BindingAdapter("availableRobots")
fun setAvailableRobotsItems(recyclerView: RecyclerView, itemList: List<ConnectRobotItem>?) {
    if (itemList != null) {
        (recyclerView.adapter as? ConnectAdapter)?.setItems(itemList)
    }
}

@BindingAdapter("priorityPrograms")
fun setPriorityPrograms(recyclerView: RecyclerView, itemList: List<ProgramPriorityItemViewModel>?) {
    if (itemList != null) {
        (recyclerView.adapter as? ProgramPriorityAdapter)?.setItems(itemList)
    }
}
