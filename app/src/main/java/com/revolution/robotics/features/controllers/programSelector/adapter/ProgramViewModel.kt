package com.revolution.robotics.features.controllers.programSelector.adapter

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.extensions.formatYearMonthDay
import com.revolution.robotics.features.controllers.programSelector.ProgramSelectorMvp
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class ProgramViewModel(private val program: UserProgram, private val presenter: ProgramSelectorMvp.Presenter) {

    companion object {
        val background = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.black)
            .borderColorResource(R.color.grey_8e)
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_8dp)
            .clipLeftSide(true)
            .create()
    }

    val programName = program.name
    val background = ProgramViewModel.background
    val formattedDate = program.lastModified.formatYearMonthDay()

    fun onProgramClicked() = presenter.onProgramSelected(program)

    fun onProgramInfoClicked() = presenter.onProgramInfoClicked(program)

    fun onEditProgramClicked() = presenter.onEditProgramClicked(program)
}
