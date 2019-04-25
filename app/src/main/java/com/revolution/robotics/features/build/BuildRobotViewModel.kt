package com.revolution.robotics.features.build

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.remote.BuildStep

class BuildRobotViewModel : ViewModel() {
    var buildStep: MutableLiveData<BuildStep?> = MutableLiveData()
}
