package com.revolution.robotics.features.coding.programs

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.local.UserProgram

class ProgramsViewModel(private val presenter: ProgramsMvp.Presenter) : ViewModel() {

    fun onProgramClicked(program: UserProgram) {
        presenter.onProgramSelected(program)
    }
}
