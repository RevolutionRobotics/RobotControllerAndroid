package com.revolution.robotics.features.controllers.buttonless

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramViewModel

interface ButtonlessProgramSelectorMvp : Mvp {

    interface View : Mvp.View {
        fun showUserProgramDialog(userProgram: UserProgram)
    }

    interface Presenter : Mvp.Presenter<View, ButtonlessProgramSelectorViewModel> {
        fun onNextButtonClicked()
        fun onSelectAllClicked(checked: Boolean)
        fun onShowCompatibleProgramsButtonClicked()
        fun updateOrderingAndFiltering()

        fun onProgramSelected(viewModel: ButtonlessProgramViewModel)
        fun onInfoButtonClicked(userProgram: UserProgram)
    }

}