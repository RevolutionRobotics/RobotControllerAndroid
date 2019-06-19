package com.revolution.robotics.features.mainmenu.settings.firmware.update

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirmwareUpdateInfoViewModel : ViewModel() {

    val robotName = MutableLiveData<String>()
    val firmwareVersion = MutableLiveData<String>()
    val hardwareVersion = MutableLiveData<String>()
    val softwareVersion = MutableLiveData<String>()
    val serialNumber = MutableLiveData<String>()
    val manufacturer = MutableLiveData<String>()
    val modelNumber = MutableLiveData<String>()
    val batteryMain = MutableLiveData<String>()
    val batteryMotor = MutableLiveData<String>()

    val updateTextVisible = MutableLiveData<Boolean>()
    val loadingTextVisible = MutableLiveData<Boolean>()
    val infoTextsVisible = MutableLiveData<Boolean>()
    val updateText = MutableLiveData<String>()

    var firmwareVersionCode: String? = null
}
