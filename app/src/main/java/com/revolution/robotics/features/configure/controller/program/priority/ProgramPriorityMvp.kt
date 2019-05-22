package com.revolution.robotics.features.configure.controller.program.priority

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram

interface ProgramPriorityMvp : Mvp {

    interface View : Mvp.View {
        fun showProgramInfoDialog(userProgram: UserProgram, compatible: Boolean)
        fun showSaveDialog(name: String?, description: String?)
    }

    interface Presenter : Mvp.Presenter<View, ProgramPriorityViewModel> {
        fun onItemMoved(from: Int, to: Int)
        fun onDragEnded()
        fun onInfoButtonClicked(item: ProgramPriorityItemViewModel)
        fun onDoneButtonClicked()
    }
}
