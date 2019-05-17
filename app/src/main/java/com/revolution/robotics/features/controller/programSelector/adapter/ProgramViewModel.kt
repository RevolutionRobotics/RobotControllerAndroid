package com.revolution.robotics.features.controller.programSelector.adapter

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.Program
import com.revolution.robotics.core.extensions.formatYearMonthDayDotted
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

data class ProgramViewModel(val program: Program) {

    companion object {
        val background = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.black)
            .borderColorResource(R.color.grey_8e)
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_8dp)
            .clipLeftSide(true)
            .create()
    }

    val background = ProgramViewModel.background
    val formattedDate = program.modificationDate.formatYearMonthDayDotted()
}