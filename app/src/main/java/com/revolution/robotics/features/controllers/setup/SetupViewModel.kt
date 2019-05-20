package com.revolution.robotics.features.controllers.setup

import androidx.lifecycle.ViewModel

class SetupViewModel(private val presenter: SetupMvp.Presenter) : ViewModel() {

    companion object {
        const val NO_PROGRAM_SELECTED = -1
    }

    // TODO finalise program structure & remove test data
    private val programs = listOf(
        "Crane lift up",
        null,
        null,
        "Crane lift down",
        null,
        null
    )

    var selectedProgram = NO_PROGRAM_SELECTED

    fun isProgramSelected(index: Int) = selectedProgram == index

    fun getProgram(index: Int) = programs[index - 1]

    fun selectProgram(index: Int) {
        selectedProgram = index
        presenter.onProgramSlotSelected(index)
    }

    fun onControllerSetupFinished() {
        presenter.onControllerSetupFinished()
    }
}
