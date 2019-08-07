package com.revolution.robotics.features.configure.controller.program.priority

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProgramPriorityViewModel : ViewModel() {

    val items = MutableLiveData<List<PriorityItem>>()

}
