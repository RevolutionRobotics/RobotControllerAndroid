package com.revolution.robotics.features.controllers.setup

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.local.UserProgram

class SetupViewModel(private val presenter: SetupMvp.Presenter) : ViewModel() {

    companion object {
        const val NO_PROGRAM_SELECTED = -1
    }

    private val programs = mutableListOf<UserProgram?>(null, null, null, null, null, null)
    private var selectedProgram = NO_PROGRAM_SELECTED

    fun isProgramSelected(index: Int) = selectedProgram == index

    fun getProgram(index: Int) =
        if (programs.size >= index && index >= 0) {
            programs[index - 1]
        } else {
            null
        }

    fun onProgramSet(program: UserProgram?) {
        programs[selectedProgram - 1] = program
    }

    fun selectProgram(index: Int) {
        selectedProgram = index
        presenter.onProgramSlotSelected(index)
    }

    fun onControllerSetupFinished() {
        presenter.onControllerSetupFinished()
    }
}
