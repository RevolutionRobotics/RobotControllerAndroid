package com.revolution.robotics.features.configure.sensor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.views.ChippedEditTextViewModel
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class SensorConfigurationPresenter(private val resourceResolver: ResourceResolver) : SensorConfigurationMvp.Presenter {

    override var view: SensorConfigurationMvp.View? = null
    override var model: SensorConfigurationViewModel? = null

    private val chippedConfigDoneEnabled = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.grey_28)
        .borderColorResource(R.color.white)
        .chipTopRight(true)
        .chipBottomLeft(false)
        .chipBottomRight(false)
        .chipTopLeft(false)
        .chipSize(R.dimen.dimen_8dp)
        .borderSize(R.dimen.dimen_1dp)
        .create()

    override fun setSensor(sensor: Sensor, portName: String) {
        model?.apply {
            editTextModel.value = ChippedEditTextViewModel(
                title = "$portName - ${resourceResolver.string(R.string.configure_motor_name_inputfield_title)}",
                text = sensor.variableName,
                borderColor = R.color.grey_8e,
                backgroundColor = R.color.grey_28,
                textColor = R.color.white,
                titleColor = R.color.white
            )

            actionButtonsViewModel.doneButtonEnabled.set(true)
            actionButtonsViewModel.doneButtonChippedBoxConfig.value = chippedConfigDoneEnabled
            actionButtonsViewModel.doneTextColor.set(R.color.white)
            bumperButton.isSelected.set(sensor.type == Sensor.TYPE_BUMPER)
            ultrasoundButton.isSelected.set(sensor.type == Sensor.TYPE_ULTRASOUND)
            emptyButton.isSelected.set(sensor.type.isNullOrEmpty())
        }
    }

    override fun onEmptyButtonClicked() {
        model?.apply {
            bumperButton.isSelected.set(false)
            ultrasoundButton.isSelected.set(false)
            emptyButton.isSelected.set(true)
            setTestButton(false)
        }
    }

    override fun onBumberButtonClicked() {
        model?.apply {
            bumperButton.isSelected.set(true)
            ultrasoundButton.isSelected.set(false)
            emptyButton.isSelected.set(false)
            setTestButton(true)
        }
    }

    override fun onUltarsoundButtonClicked() {
        model?.apply {
            bumperButton.isSelected.set(false)
            ultrasoundButton.isSelected.set(true)
            emptyButton.isSelected.set(false)
            setTestButton(true)
        }
    }

    private fun setTestButton(enabled: Boolean) {
        model?.apply {
            actionButtonsViewModel.testButtonEnabled.set(enabled)
            actionButtonsViewModel.testTextColor.set(if (enabled) R.color.white else R.color.grey_8e)
        }
    }

    override fun onTestButtonClcked() {
        // TODO Handle test button
    }

    override fun onDoneButtonClicked() {
        // TODO Handle done button
    }
}
