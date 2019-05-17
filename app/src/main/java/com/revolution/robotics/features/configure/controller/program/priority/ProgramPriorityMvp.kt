package com.revolution.robotics.features.configure.controller.program.priority

import com.revolution.robotics.core.Mvp

interface ProgramPriorityMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, ProgramPriorityViewModel> {
        fun onItemMoved(from: Int, to: Int)
        fun onDragEnded()
    }
}
