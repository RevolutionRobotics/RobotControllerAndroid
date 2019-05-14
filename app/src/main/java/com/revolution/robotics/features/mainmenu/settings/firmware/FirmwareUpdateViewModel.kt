package com.revolution.robotics.features.mainmenu.settings.firmware

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirmwareUpdateViewModel(private val presenter: FirmwareMvp.Presenter) : ViewModel() {

    val hasConnectedRobot = MutableLiveData<Boolean>()
    val robotName = MutableLiveData<String>()

    fun onConnectClicked() {
        presenter.onConnectClicked()
    }

    fun onRobotClicked() {
        presenter.onRobotClicked()
    }
}