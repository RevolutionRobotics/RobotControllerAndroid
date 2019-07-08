package com.revolution.robotics.features.configure.connections

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigureConnectionsViewModel : ViewModel() {
    var partM1: MutableLiveData<RobotPartModel> = MutableLiveData()
    var partM2: MutableLiveData<RobotPartModel> = MutableLiveData()
    var partM3: MutableLiveData<RobotPartModel> = MutableLiveData()
    var partM4: MutableLiveData<RobotPartModel> = MutableLiveData()
    var partM5: MutableLiveData<RobotPartModel> = MutableLiveData()
    var partM6: MutableLiveData<RobotPartModel> = MutableLiveData()

    var partS1: MutableLiveData<RobotPartModel> = MutableLiveData()
    var partS2: MutableLiveData<RobotPartModel> = MutableLiveData()
    var partS3: MutableLiveData<RobotPartModel> = MutableLiveData()
    var partS4: MutableLiveData<RobotPartModel> = MutableLiveData()

    var robotId: MutableLiveData<Int> = MutableLiveData()
    var firebaseImageUrl: MutableLiveData<String?> = MutableLiveData()
}
