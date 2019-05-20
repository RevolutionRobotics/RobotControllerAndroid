package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel

interface SetupMvp : Mvp {

    interface View : Mvp.View {
        fun onProgramSlotSelected(index: Int, mostRecent: MostRecentProgramViewModel)
        fun onShowAllProgramsSelected()
        fun addProgram(program: UserProgram)
    }

    interface Presenter : Mvp.Presenter<View, SetupViewModel> {
        fun onProgramSlotSelected(index: Int)
        fun showAllPrograms()
        fun onControllerSetupFinished()
        fun addProgram(program: UserProgram)
    }
}
