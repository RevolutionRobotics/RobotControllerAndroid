package com.revolution.robotics.features.coding.programs.adapter

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.extensions.formatYearMonthDay
import com.revolution.robotics.features.coding.programs.ProgramsMvp
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class ProgramViewModel(private val program: UserProgram, robot: UserRobot?, private val presenter: ProgramsMvp.Presenter) {

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
    val robotName = robot?.name ?: "-"

    fun onProgramClicked() {
        presenter.onProgramSelected(program)
    }
}
