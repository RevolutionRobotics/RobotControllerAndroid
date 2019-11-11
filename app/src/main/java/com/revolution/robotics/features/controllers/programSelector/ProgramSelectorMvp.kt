package com.revolution.robotics.features.controllers.programSelector

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.configure.controller.ControllerButton
import com.revolution.robotics.features.controllers.programSelector.adapter.ProgramViewModel

interface ProgramSelectorMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(baseDialog: BaseDialog)
        fun onProgramsChanged(programs: List<ProgramViewModel>)
    }

    interface Presenter : Mvp.Presenter<View, ProgramSelectorViewModel> {
        fun loadPrograms(controllerButton: ControllerButton, robotId: Int)
        fun back()
        fun updateOrderingAndFiltering()
        fun showCompatibleProgramsClicked()
        fun onProgramSelected(userProgram: UserProgram)
        fun onProgramInfoClicked(userProgram: UserProgram)
        fun onEditProgramClicked(userProgram: UserProgram)
        fun clearSelectionStates()
        fun addProgram(userProgram: UserProgram)
    }
}
