package com.revolution.robotics.features.controllers.programSelector

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.controllers.programSelector.adapter.ProgramViewModel
import com.revolution.robotics.views.dialogs.RoboticsDialog

interface ProgramSelectorMvp : Mvp {

    interface View : Mvp.View {
        fun onProgramsChanged(programs: List<ProgramViewModel>)
        fun showDialog(roboticsDialog: RoboticsDialog)
    }

    interface Presenter : Mvp.Presenter<View, ProgramSelectorViewModel> {
        fun back()
        fun updateOrderingAndFiltering()
        fun showCompatibleProgramsClicked()
        fun onProgramSelected(userProgram: UserProgram)
    }
}
