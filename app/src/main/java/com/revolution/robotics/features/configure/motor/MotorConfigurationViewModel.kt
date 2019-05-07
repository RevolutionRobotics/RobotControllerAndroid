package com.revolution.robotics.features.configure.motor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.features.configure.ConfigButtonViewModel
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class MotorConfigurationViewModel(
    private val presenter: MotorConfigurationMvp.Presenter
) : ViewModel() {

    val emptyButton = ConfigButtonViewModel(R.string.configuration_empty, R.drawable.ic_empty, ::onEmptyButtonClicked)
    val driveTrainButton =
        ConfigButtonViewModel(R.string.configuration_drivetrain, R.drawable.ic_wheel_grey, ::onDrivetrainButtonClicked)
    val motorButton = ConfigButtonViewModel(R.string.configuration_motor, R.drawable.ic_engine, ::onMotorClicked)
    val sideLeftButton =
        ConfigButtonViewModel(R.string.configuration_left, R.drawable.ic_wheel_left_grey, ::onLeftSideClicked)
    val sideRightButton =
        ConfigButtonViewModel(R.string.configuration_right, R.drawable.ic_wheel_right, ::onRightSideClicked)
    val counterClockwiseButton =
        ConfigButtonViewModel(
            R.string.configuration_counter_clockwise,
            R.drawable.ic_rotation_left,
            ::onCounterClockwiseClicked
        )
    val clockwiseButton =
        ConfigButtonViewModel(R.string.configuration_clockwise, R.drawable.ic_rotation_right_grey, ::onClockwiseClicked)
    val motorCounterClockwiseButton =
        ConfigButtonViewModel(
            R.string.configuration_counter_clockwise,
            R.drawable.ic_rotation_left,
            ::onCounterClockwiseClicked
        )
    val motorClockwiseButton =
        ConfigButtonViewModel(R.string.configuration_clockwise, R.drawable.ic_rotation_right_grey, ::onClockwiseClicked)

    val editTextTitle = MutableLiveData<String>()
    val testButtonChippedBoxConfig = MutableLiveData<ChippedBoxConfig>()
    val doneButtonChippedBoxConfig = MutableLiveData<ChippedBoxConfig>()

    fun onEmptyButtonClicked() {
        presenter.onEmptyButtonClicked()
    }

    fun onDrivetrainButtonClicked() {
        presenter.onDrivetrainButtonClicked()
    }

    fun onMotorClicked() {
        presenter.onMotorClicked()
    }

    fun onLeftSideClicked() {
        presenter.onLeftSideClicked()
    }

    fun onRightSideClicked() {
        presenter.onRightSideClicked()
    }

    fun onCounterClockwiseClicked() {
        presenter.onCounterClockwiseClicked()
    }

    fun onClockwiseClicked() {
        presenter.onClockwiseClicked()
    }
}