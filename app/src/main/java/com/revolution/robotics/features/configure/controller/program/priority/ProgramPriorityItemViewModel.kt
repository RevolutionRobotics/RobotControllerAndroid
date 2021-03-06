package com.revolution.robotics.features.configure.controller.program.priority

import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.formatYearMonthDay
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

open class ProgramPriorityItemViewModel(
    val userProgramBindingItem: UserProgramBindingItem,
    position: Int,
    private val presenter: ProgramPriorityMvp.Presenter
) :
    PriorityItem(position) {

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

    val selectedBackground = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.grey_1d)
        .borderColorResource(R.color.robotics_red)
        .chipBottomLeft(true)
        .chipTopRight(true)
        .chipSize(R.dimen.dimen_6dp)
        .clipLeftSide(true)
        .borderSize(R.dimen.dimen_1dp)
        .create()

    val isItemSelected = ObservableBoolean(false)
    val positionText: String
        get() {
            return "$position."
        }
    val formattedDate = userProgramBindingItem.lastModified.formatYearMonthDay()
    val name = userProgramBindingItem.name
    val icon = if (userProgramBindingItem.type == ProgramType.BACKGROUND) {
        R.drawable.ic_bg_program
    } else {
        R.drawable.ic_controller
    }

    fun onInfoButtonClicked() {
        presenter.onInfoButtonClicked(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProgramPriorityItemViewModel

        if (userProgramBindingItem != other.userProgramBindingItem) return false
        if (position != other.position) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userProgramBindingItem.hashCode()
        result = 31 * result + position
        return result
    }
}
