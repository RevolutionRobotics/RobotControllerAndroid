package com.revolution.robotics.features.configure.controller.program.priority

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp

interface ProgramPriorityMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(baseDialog: BaseDialog)
    }

    interface Presenter : Mvp.Presenter<View, ProgramPriorityViewModel> {
        fun loadPrograms(controllerId: Int)
        fun onItemMoved(from: Int, to: Int)
        fun onDragEnded()
        fun onInfoButtonClicked(item: ProgramPriorityItemViewModel)
        fun save()
    }
}
