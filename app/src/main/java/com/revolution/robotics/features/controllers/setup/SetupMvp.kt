package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel
import com.revolution.robotics.views.dialogs.RoboticsDialog

interface SetupMvp : Mvp {

    interface View : Mvp.View {
        fun onProgramSlotSelected(index: Int, mostRecent: MostRecentProgramViewModel)
        fun onShowAllProgramsSelected()
        fun showDialog(roboticsDialog: RoboticsDialog)
    }

    interface Presenter : Mvp.Presenter<View, SetupViewModel> {
        fun onProgramSlotSelected(index: Int)
        fun showAllPrograms()
        fun onControllerSetupFinished()
        fun addProgram(program: UserProgram)
        fun removeProgram(program: UserProgram)
    }
}