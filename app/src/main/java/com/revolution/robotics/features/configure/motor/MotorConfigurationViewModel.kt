package com.revolution.robotics.features.configure.motor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class MotorConfigurationViewModel(
    private val resourceResolver: ResourceResolver,
    private val presenter: MotorConfigurationMvp.Presenter
) : ViewModel() {

    val editTextTitle = MutableLiveData<String>()
    val isMotor = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isDriveTrain = MutableLiveData<Boolean>()
    val isRightDirection = MutableLiveData<Boolean>()
    val isClockwise = MutableLiveData<Boolean>()

    fun setMotorData(motor: Motor, portName: String) {
        editTextTitle.value = "$portName${resourceResolver.string(R.string.config_name_edit_title)}"
        isMotor.value = motor.type == "motor"
        isDriveTrain.value = motor.type == "drivetrain"
        isRightDirection.value = motor.side == "right"
        isClockwise.value = motor.rotation == "clockwise"
    }
}