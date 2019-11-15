package com.revolution.robotics.features.configure.sensor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.features.configure.ActionButtonsViewModel
import com.revolution.robotics.features.configure.ConfigButtonViewModel
import com.revolution.robotics.views.ChippedEditTextViewModel

class SensorConfigurationViewModel(private val presenter: SensorConfigurationMvp.Presenter) : ViewModel() {

    val editTextModel = MutableLiveData<ChippedEditTextViewModel>()

    val emptyButton =
        ConfigButtonViewModel(R.string.configure_motor_empty_button_title, R.drawable.ic_empty, ::onEmptyButtonClicked)
    val bumperButton =
        ConfigButtonViewModel(
            R.string.configure_sensor_bumper_button_title,
            R.drawable.ic_bumper,
            presenter::onBumperButtonClicked
        )
    val ultrasoundButton =
        ConfigButtonViewModel(
            R.string.configure_sensor_ultrasound_button_title,
            R.drawable.ic_ultrasound,
            presenter::onUltrasoundButtonClicked
        )

    val actionButtonsViewModel =
        ActionButtonsViewModel({ presenter.onDoneButtonClicked() }, { presenter.onTestButtonClicked() })

    private fun onEmptyButtonClicked() {
        presenter.onEmptyButtonClicked()
    }
}
