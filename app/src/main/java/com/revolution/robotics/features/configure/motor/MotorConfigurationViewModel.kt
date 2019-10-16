package com.revolution.robotics.features.configure.motor

import androidx.databinding.ObservableBoolean
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
        ConfigButtonViewModel(
            R.string.configure_motor_empty_button_title,
            R.drawable.ic_empty,
            presenter::onEmptyButtonClicked
        )
    val driveTrainButton =
        ConfigButtonViewModel(
            R.string.configure_motor_drivetrain_button_title,
            R.drawable.ic_wheel_grey,
            presenter::onDrivetrainButtonClicked
        )
    val motorButton =
        ConfigButtonViewModel(
            R.string.configure_motor_motor_button_title,
            R.drawable.ic_engine,
            presenter::onMotorClicked
        )
    val sideLeftButton =
        ConfigButtonViewModel(
            R.string.configure_motor_left_button_title,
            R.drawable.ic_wheel_left_grey,
            presenter::onLeftSideClicked
        )
    val sideRightButton =
        ConfigButtonViewModel(
            R.string.configure_motor_right_button_title,
            R.drawable.ic_wheel_right,
            presenter::onRightSideClicked
        )

    var reversedCheckboxVisible = ObservableBoolean(false)
    var reversed = ObservableBoolean(false)

    fun onReversedChecked(checked: Boolean) = presenter.onReversedChanged(checked)


    val editTextModel = MutableLiveData<ChippedEditTextViewModel>()

    val actionButtonsViewModel =
        ActionButtonsViewModel({ presenter.onDoneButtonClicked() }, { presenter.onTestButtonClicked() })
}
