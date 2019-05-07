package com.revolution.robotics.features.configure.motor

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.features.configure.ConfigButtonViewModel
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class MotorConfigurationViewModel(
    private val presenter: MotorConfigurationMvp.Presenter
) : ViewModel() {

    val emptyButton = ConfigButtonViewModel(R.string.configure_motor_empty_button_title, R.drawable.ic_empty, ::onEmptyButtonClicked)
    val driveTrainButton =
        ConfigButtonViewModel(R.string.configure_motor_drivetrain_button_title, R.drawable.ic_wheel_grey, ::onDrivetrainButtonClicked)
    val motorButton = ConfigButtonViewModel(R.string.configure_motor_motor_button_title, R.drawable.ic_engine, ::onMotorClicked)
    val sideLeftButton =
        ConfigButtonViewModel(R.string.configure_motor_left_button_title, R.drawable.ic_wheel_left_grey, ::onLeftSideClicked)
    val sideRightButton =
        ConfigButtonViewModel(R.string.configure_motor_right_button_title, R.drawable.ic_wheel_right, ::onRightSideClicked)
    val counterClockwiseButton =
        ConfigButtonViewModel(
            R.string.configure_motor_counterclockwise_button_title,
            R.drawable.ic_rotation_left,
            ::onCounterClockwiseClicked
        )
    val clockwiseButton =
        ConfigButtonViewModel(R.string.configure_motor_clockwise_button_title, R.drawable.ic_rotation_right_grey, ::onClockwiseClicked)
    val motorCounterClockwiseButton =
        ConfigButtonViewModel(
            R.string.configure_motor_counterclockwise_button_title,
            R.drawable.ic_rotation_left,
            ::onCounterClockwiseClicked
        )
    val motorClockwiseButton =
        ConfigButtonViewModel(R.string.configure_motor_clockwise_button_title, R.drawable.ic_rotation_right_grey, ::onClockwiseClicked)

    val editTextTitle = MutableLiveData<String>()
    val testButtonChippedBoxConfig = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.grey_1e)
        .borderColorResource(R.color.grey_1e)
        .chipBottomLeft(true)
        .chipTopRight(false)
        .chipBottomRight(false)
        .chipTopLeft(false)
        .chipSize(R.dimen.dimen_8dp)
        .borderSize(R.dimen.dimen_1dp)
        .create()
    val doneTextColor = ObservableInt()
    val doneButtonEnabled = ObservableBoolean()
    val testTextColor = ObservableInt()
    val testButtonEnabled = ObservableBoolean()

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

    fun onTestButtonClicked() {
        presenter.onTestButtonClcked()
    }

    fun onDoneButtonClicked() {
        presenter.onDoneButtonClicked()
    }
}