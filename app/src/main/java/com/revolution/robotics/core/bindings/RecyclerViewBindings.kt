package com.revolution.robotics.core.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.features.build.connect.adapter.ConnectAdapter
import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem
import com.revolution.robotics.features.configure.controller.program.priority.ProgramPriorityAdapter
import com.revolution.robotics.features.configure.controller.program.priority.ProgramPriorityItemViewModel
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramAdapter
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramViewModel

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

@BindingAdapter("buttonlessPrograms")
fun setButtonlessPrograms(recyclerView: RecyclerView, itemList: List<ButtonlessProgramViewModel>?) {
    if (itemList != null) {
        (recyclerView.adapter as? ButtonlessProgramAdapter)?.setItems(itemList)
    }
}
