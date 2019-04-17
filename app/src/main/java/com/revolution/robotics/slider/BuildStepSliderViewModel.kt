package com.revolution.robotics.slider

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.remote.BuildStep

class BuildStepSliderViewModel : ViewModel() {

    val buildSteps: MutableLiveData<List<BuildStep>> = MutableLiveData()
}
