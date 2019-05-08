package com.revolution.robotics.features.build.connect.availableRobotsFace

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem

class ConnectViewModel : ViewModel() {
    val availableRobots: MutableLiveData<List<ConnectRobotItem>> = MutableLiveData()
    var isDiscovering = ObservableBoolean()

    init {
        isDiscovering.set(false)
    }
}
