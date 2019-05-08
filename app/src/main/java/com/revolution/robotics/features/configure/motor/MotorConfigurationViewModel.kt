package com.revolution.robotics.features.configure.motor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.features.configure.ActionButtonsViewModel
import com.revolution.robotics.features.configure.ConfigButtonViewModel
import com.revolution.robotics.views.ChippedEditTextViewModel

class MotorConfigurationViewModel(
    private val presenter: MotorConfigurationMvp.Presenter
) : ViewModel() {

    val emptyButton =
        ConfigButtonViewModel(R.string.configure_motor_empty_button_title, R.drawable.ic_empty, ::onEmptyButtonClicked)
    val driveTrainButton =
        ConfigButtonViewModel(
            R.string.configure_motor_drivetrain_button_title,
            R.drawable.ic_wheel_grey,
            ::onDrivetrainButtonClicked
        )
    val motorButton =
        ConfigButtonViewModel(R.string.configure_motor_motor_button_title, R.drawable.ic_engine, ::onMotorClicked)
    val sideLeftButton =
        ConfigButtonViewModel(
            R.string.configure_motor_left_button_title,
            R.drawable.ic_wheel_left_grey,
            ::onLeftSideClicked
        )
    val sideRightButton =
        ConfigButtonViewModel(
            R.string.configure_motor_right_button_title,
            R.drawable.ic_wheel_right,
            ::onRightSideClicked
        )
    val counterClockwiseButton =
        ConfigButtonViewModel(
            R.string.configure_motor_counterclockwise_button_title,
            R.drawable.ic_rotation_left,
            ::onCounterClockwiseClicked
        )
    val clockwiseButton =
        ConfigButtonViewModel(
            R.string.configure_motor_clockwise_button_title,
            R.drawable.ic_rotation_right_grey,
            ::onClockwiseClicked
        )
    val motorCounterClockwiseButton =
        ConfigButtonViewModel(
            R.string.configure_motor_counterclockwise_button_title,
            R.drawable.ic_rotation_left,
            ::onCounterClockwiseClicked
        )
    val motorClockwiseButton =
        ConfigButtonViewModel(
            R.string.configure_motor_clockwise_button_title,
            R.drawable.ic_rotation_right_grey,
            ::onClockwiseClicked
        )

    val editTextModel = MutableLiveData<ChippedEditTextViewModel>()

    val actionButtonsViewModel =
        ActionButtonsViewModel({ presenter.onDoneButtonClicked() }, { presenter.onTestButtonClcked() })

    private fun onEmptyButtonClicked() {
        presenter.onEmptyButtonClicked()
    }

    private fun onDrivetrainButtonClicked() {
        presenter.onDrivetrainButtonClicked()
    }

    private fun onMotorClicked() {
        presenter.onMotorClicked()
    }

    private fun onLeftSideClicked() {
        presenter.onLeftSideClicked()
    }

    private fun onRightSideClicked() {
        presenter.onRightSideClicked()
    }

    private fun onCounterClockwiseClicked() {
        presenter.onCounterClockwiseClicked()
    }

    private fun onClockwiseClicked() {
        presenter.onClockwiseClicked()
    }
}
