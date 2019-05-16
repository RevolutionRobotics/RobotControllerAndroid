package com.revolution.robotics.features.controllers.setup

import androidx.lifecycle.ViewModel

class SetupViewModel(private val presenter: SetupMvp.Presenter) : ViewModel() {
    private val programs = listOf(
        "Crane lift up",
        null,
        null,
        "Crane lift down",
        null,
        null
    )

    var selectedProgram = 2

    fun isProgramSelected(index: Int) = selectedProgram == index

    fun getProgram(index: Int) = programs[index - 1]

    fun selectProgram(index: Int) {
        selectedProgram = index
        presenter.onProgramSlotSelected(index, this)
    }
}
