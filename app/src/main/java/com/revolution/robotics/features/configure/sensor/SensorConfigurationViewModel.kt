package com.revolution.robotics.features.configure.sensor

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.features.configure.ActionButtonsViewModel
import com.revolution.robotics.features.configure.ConfigButtonViewModel
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class SensorConfigurationViewModel(private val presenter: SensorConfigurationMvp.Presenter) : ViewModel() {

    val editTextTitle = MutableLiveData<String>()

    val emptyButton =
        ConfigButtonViewModel(R.string.configure_motor_empty_button_title, R.drawable.ic_empty, ::onEmptyButtonClicked)
    val bumperButton =
        ConfigButtonViewModel(
            R.string.configure_sensor_bumper_button_title,
            R.drawable.ic_bumper,
            ::onBumperButtonClicked
        )
    val ultrasoundButton =
        ConfigButtonViewModel(
            R.string.configure_sensor_ultrasound_button_title,
            R.drawable.ic_ultrasound,
            ::onUltrasoundButtonClicked
        )

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
    val actionButtonsViewModel =
        ActionButtonsViewModel({ presenter.onDoneButtonClicked() }, { presenter.onTestButtonClcked() })

    private fun onEmptyButtonClicked() {
        presenter.onEmptyButtonClicked()
    }

    private fun onBumperButtonClicked() {
        presenter.onBumberButtonClicked()
    }

    private fun onUltrasoundButtonClicked() {
        presenter.onUltarsoundButtonClicked()
    }
}
