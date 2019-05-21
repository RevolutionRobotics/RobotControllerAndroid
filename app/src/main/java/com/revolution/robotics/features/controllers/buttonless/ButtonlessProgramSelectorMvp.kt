package com.revolution.robotics.features.controllers.buttonless

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram

interface ButtonlessProgramSelectorMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, ButtonlessProgramSelectorViewModel> {
        fun onNextButtonClicked()
        fun onSelectAllClicked(checked: Boolean)
        fun onShowCompatibleProgramsButtonClicked()
        fun updateOrderingAndFiltering()

        fun onProgramSelected(userProgram: UserProgram)
        fun onInfoButtonClicked(userProgram: UserProgram)
    }

}