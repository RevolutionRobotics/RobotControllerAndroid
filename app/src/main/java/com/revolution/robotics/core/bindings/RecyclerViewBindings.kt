package com.revolution.robotics.core.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.features.build.connect.adapter.ConnectAdapter
import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem
import com.revolution.robotics.features.challenges.challengeDetail.adapter.ChallengePartAdapter
import com.revolution.robotics.features.challenges.challengeDetail.adapter.ChallengePartItemViewModel
import com.revolution.robotics.features.challenges.challengeGroup.adapter.ChallengeGroupAdapter
import com.revolution.robotics.features.challenges.challengeGroup.adapter.ChallengeGroupItem
import com.revolution.robotics.features.challenges.challengeList.adapter.ChallengeListAdapter
import com.revolution.robotics.features.challenges.challengeList.adapter.ChallengeListItem
import com.revolution.robotics.features.configure.controller.program.priority.ProgramPriorityAdapter
import com.revolution.robotics.features.configure.controller.program.priority.ProgramPriorityItemViewModel
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramAdapter
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramViewModel

@BindingAdapter("availableRobots")
fun setAvailableRobotsItems(recyclerView: RecyclerView, items: Set<ConnectRobotItem>?) {
    if (items != null) {
        (recyclerView.adapter as? ConnectAdapter)?.setItems(items.toList())
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

@BindingAdapter("challengeGroups")
fun setChallengeGroups(recyclerView: RecyclerView, itemList: List<ChallengeGroupItem>?) {
    if (itemList != null) {
        (recyclerView.adapter as? ChallengeGroupAdapter)?.setItems(itemList)
    }
}

@BindingAdapter("challengeSteps")
fun setChallengeSteps(recyclerView: RecyclerView, itemList: List<ChallengeListItem>?) {
    if (itemList != null) {
        (recyclerView.adapter as? ChallengeListAdapter)?.setItems(itemList)
    }
}

@BindingAdapter("challengeParts")
fun setChallengeParts(recyclerView: RecyclerView, itemList: List<ChallengePartItemViewModel>?) {
    if (itemList != null) {
        (recyclerView.adapter as? ChallengePartAdapter)?.setItems(itemList)
    }
}
