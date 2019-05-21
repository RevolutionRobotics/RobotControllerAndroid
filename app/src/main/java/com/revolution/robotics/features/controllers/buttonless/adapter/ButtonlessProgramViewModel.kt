package com.revolution.robotics.features.controllers.buttonless.adapter

import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.extensions.formatYearMonthDayDotted
import com.revolution.robotics.features.controllers.buttonless.ButtonlessProgramSelectorMvp
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import java.util.Date

class ButtonlessProgramViewModel(
    val program: UserProgram,
    private val presenter: ButtonlessProgramSelectorMvp.Presenter
) {

    companion object {
        val background = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.black)
            .borderColorResource(R.color.grey_8e)
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_8dp)
            .clipLeftSide(true)
            .create()

        val selectedBackground = ChippedBoxConfig.Builder()
            .backgroundColorResource(R.color.black)
            .borderColorResource(R.color.robotics_red)
            .borderSize(R.dimen.dimen_1dp)
            .chipSize(R.dimen.dimen_8dp)
            .clipLeftSide(true)
            .create()
    }

    val selected = ObservableBoolean(false)
    val enabled = ObservableBoolean(true)

    val programName = program.name
    val background = ButtonlessProgramViewModel.background
    val selectedBackground = ButtonlessProgramViewModel.selectedBackground
    val formattedDate = Date(program.lastModified).formatYearMonthDayDotted()

    fun onProgramClicked() {
        presenter.onProgramSelected(this)
    }

    fun onInfoButtonClicked() {
        presenter.onInfoButtonClicked(program)
    }
}