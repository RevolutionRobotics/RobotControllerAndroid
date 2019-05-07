package com.revolution.robotics.features.configure.motor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.configure.ConfigButtonViewModel

class MotorConfigurationViewModel(
    private val resourceResolver: ResourceResolver,
    private val presenter: MotorConfigurationMvp.Presenter
) : ViewModel() {

    val emptyButton = ConfigButtonViewModel(R.string.configuration_empty, R.drawable.ic_empty)
    val driveTrainButton = ConfigButtonViewModel(R.string.configuration_drivetrain, R.drawable.ic_wheel_grey)
    val motorButton = ConfigButtonViewModel(R.string.configuration_motor, R.drawable.ic_engine)
    val sideLeftButton = ConfigButtonViewModel(R.string.configuration_left, R.drawable.ic_wheel_left_grey)
    val sideRightButton = ConfigButtonViewModel(R.string.configuration_right, R.drawable.ic_wheel_right)
    val counterClockwiseButton =
        ConfigButtonViewModel(R.string.configuration_counter_clockwise, R.drawable.ic_rotation_left)
    val clockwiseButton = ConfigButtonViewModel(R.string.configuration_clockwise, R.drawable.ic_rotation_right_grey)


    val editTextTitle = MutableLiveData<String>()

    fun setMotorData(motor: Motor, portName: String) {
        editTextTitle.value = "$portName${resourceResolver.string(R.string.config_name_edit_title)}"
    }
}