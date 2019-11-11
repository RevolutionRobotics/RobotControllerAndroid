package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.configure.controller.ControllerButton
import com.revolution.robotics.features.controllers.setup.mostRecent.MostRecentProgramViewModel

interface ConfigureControllerMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(baseDialog: BaseDialog)
        fun updateContentBindings()
        fun onProgramSlotSelected(index: Int, mostRecent: MostRecentProgramViewModel?)
        fun showAllPrograms(controllerButton: ControllerButton, robotId: Int)
        fun navigateToEditProgram(userProgram: UserProgram?)
        fun hideProgramSelector()
    }

    interface Presenter : Mvp.Presenter<View, ConfigureControllerViewModel> {
        fun loadControllerAndPrograms(robotId: Int)
        fun onProgramSlotSelected(index: Int)
        fun showAllPrograms()
        fun showProgramDialog(program: UserProgram, isBound: Boolean)
        fun onProgramSelected(program: UserProgram, isBound: Boolean)
        fun addProgram(program: UserProgram)
        fun removeProgram()
        fun onProgramEdited(program: UserProgram)
        fun play()
    }
}
