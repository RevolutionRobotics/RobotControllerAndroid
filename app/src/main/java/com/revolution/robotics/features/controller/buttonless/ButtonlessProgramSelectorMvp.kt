package com.revolution.robotics.features.controller.buttonless

import com.revolution.robotics.core.Mvp

interface ButtonlessProgramSelectorMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, ButtonlessProgramSelectorViewModel> {
        fun onNextButtonClicked()
        fun onSelectAllClicked(checked: Boolean)
        fun onShowCompatibleProgramsButtonClicked()
        fun updateOrdering()
    }

}