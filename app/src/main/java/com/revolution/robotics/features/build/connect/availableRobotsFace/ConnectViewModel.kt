package com.revolution.robotics.features.build.connect.availableRobotsFace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem

class ConnectViewModel : ViewModel() {
    val availableRobots: MutableLiveData<List<ConnectRobotItem>> = MutableLiveData()
    var isDiscovering: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isDiscovering.value = true
    }
}
