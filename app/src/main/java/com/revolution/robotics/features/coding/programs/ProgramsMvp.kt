package com.revolution.robotics.features.coding.programs

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.coding.programs.adapter.ProgramViewModel

interface ProgramsMvp : Mvp {

    interface View : Mvp.View {
        fun onProgramsLoaded(programs: List<ProgramViewModel>)
        fun onProgramSelected(program: UserProgram)
    }

    interface Presenter : Mvp.Presenter<View, ProgramsViewModel> {
        fun loadPrograms()
        fun onProgramSelected(program: UserProgram)
    }
}
