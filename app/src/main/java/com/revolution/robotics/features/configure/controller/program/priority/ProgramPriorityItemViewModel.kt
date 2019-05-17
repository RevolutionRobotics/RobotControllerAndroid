package com.revolution.robotics.features.configure.controller.program.priority

import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.formatYearMonthDay
import com.revolution.robotics.core.utils.recyclerview.DiffUtilRecyclerAdapter
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class ProgramPriorityItemViewModel(private val userProgramBindingItem: UserProgramBindingItem, val position: Int) :
    DiffUtilRecyclerAdapter.BaseListViewModel() {

    override val idField = userProgramBindingItem.id

    val background = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.grey_1d)
        .borderColorResource(R.color.grey_8e)
        .chipBottomLeft(true)
        .chipTopRight(true)
        .chipSize(R.dimen.dimen_6dp)
        .clipLeftSide(true)
        .borderSize(R.dimen.dimen_1dp)
        .create()
    val positionText = "$position."
    val formattedDate = userProgramBindingItem.lastModified.formatYearMonthDay()
    val name = userProgramBindingItem.name
    val icon = if (userProgramBindingItem.type == ProgramType.BACKGROUND) {
        R.drawable.ic_bg_program
    } else {
        R.drawable.ic_controller
    }

    fun onOrderingIconClicked() {

    }

    fun onInfoButtonClicked() {

    }

    fun onInfoIconClicked() {

    }

    override fun equals(other: Any?): Boolean = userProgramBindingItem == other

    override fun hashCode(): Int = userProgramBindingItem.hashCode()

}