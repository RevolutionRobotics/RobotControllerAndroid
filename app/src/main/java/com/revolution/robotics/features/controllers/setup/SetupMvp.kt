package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel

interface SetupMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(baseDialog: BaseDialog)
        fun updateContentBindings()
        fun onProgramSlotSelected(index: Int, mostRecent: MostRecentProgramViewModel?)
        fun onShowAllProgramsSelected()
        fun navigateToBackgroundPrograms()
        fun navigateToEditProgram(userProgram: UserProgram?)
        fun removeSelectedProgram()
    }

    interface Presenter : Mvp.Presenter<View, SetupViewModel> {
        fun loadControllerAndPrograms(controllerId: Int)
        fun onProgramSlotSelected(index: Int)
        fun showAllPrograms()
        fun onControllerSetupFinished()
        fun addProgram(program: UserProgram)
        fun removeProgram(program: UserProgram)
        fun onProgramEdited(program: UserProgram)
    }
}
