package com.revolution.robotics.features.configure.controller.program.priority

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProgramPriorityViewModel(private val presenter: ProgramPriorityMvp.Presenter) : ViewModel() {

    val items = MutableLiveData<List<ProgramPriorityItemViewModel>>()

    fun onDoneButtonClicked() {
        presenter.onDoneButtonClicked()
    }
}
