package com.revolution.robotics.features.build.connect.availableRobotsFace

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem

class ConnectViewModel : ViewModel() {
    val availableRobots: MutableLiveData<MutableSet<ConnectRobotItem>> = MutableLiveData()
    var isDiscovering = ObservableBoolean(true)
}
