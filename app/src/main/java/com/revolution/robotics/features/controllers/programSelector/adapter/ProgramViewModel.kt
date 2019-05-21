package com.revolution.robotics.features.controllers.programSelector.adapter

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.extensions.formatYearMonthDayDotted
import com.revolution.robotics.features.controllers.programSelector.ProgramSelectorMvp
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import java.util.Date

@Suppress("UseDataClass")
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
    val formattedDate = Date(program.lastModified).formatYearMonthDayDotted()

    fun onProgramClicked() {
        presenter.onProgramSelected(program)
    }
}
