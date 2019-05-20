package com.revolution.robotics.features.controllers.programSelector

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.features.controllers.programSelector.adapter.ProgramViewModel

interface ProgramSelectorMvp : Mvp {

    interface View : Mvp.View {
        fun onProgramsChanged(programs: List<ProgramViewModel>)
    }

    interface Presenter : Mvp.Presenter<View, ProgramSelectorViewModel> {
        fun back()
        fun updateOrdering()
        fun showCompatibleProgramsClicked()
    }
}
