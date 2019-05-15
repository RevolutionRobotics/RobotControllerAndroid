package com.revolution.robotics.features.configure.sensor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.build.testing.BumperTestDialog
import com.revolution.robotics.features.build.testing.UltrasonicTestDialog
import com.revolution.robotics.features.configure.ConfigurationEventBus
import com.revolution.robotics.features.configure.SensorPort
import com.revolution.robotics.views.ChippedEditTextViewModel
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class SensorConfigurationPresenter(
    private val resourceResolver: ResourceResolver,
    private val configurationEventBus: ConfigurationEventBus
) : SensorConfigurationMvp.Presenter {

    override var view: SensorConfigurationMvp.View? = null
    override var model: SensorConfigurationViewModel? = null

    private var portName: String? = null
    private var sensor: Sensor? = null

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
        this.portName = portName
        this.sensor = sensor

        model?.apply {
            editTextModel.value = ChippedEditTextViewModel(
                title = "$portName - ${resourceResolver.string(R.string.configure_motor_name_inputfield_title)}",
                text = sensor.variableName,
                borderColor = R.color.grey_8e,
                backgroundColor = R.color.grey_28,
                textColor = R.color.white,
                titleColor = R.color.white
            )

            actionButtonsViewModel.testButtonEnabled.set(sensor.isTestable())
            actionButtonsViewModel.doneButtonEnabled.set(true)
            actionButtonsViewModel.doneButtonChippedBoxConfig.value = chippedConfigDoneEnabled
            actionButtonsViewModel.doneTextColor.set(R.color.white)
            bumperButton.isSelected.set(sensor.type == Sensor.TYPE_BUMPER)
            ultrasoundButton.isSelected.set(sensor.type == Sensor.TYPE_ULTRASONIC)
            emptyButton.isSelected.set(sensor.type.isNullOrEmpty())
        }
    }

    override fun onVariableNameChanged(name: String?) {
        sensor?.variableName = name
    }

    override fun onEmptyButtonClicked() {
        model?.apply {
            bumperButton.isSelected.set(false)
            ultrasoundButton.isSelected.set(false)
            emptyButton.isSelected.set(true)
            setTestButton(false)
        }
    }

    override fun onBumperButtonClicked() {
        model?.apply {
            bumperButton.isSelected.set(true)
            ultrasoundButton.isSelected.set(false)
            emptyButton.isSelected.set(false)
            setTestButton(true)
        }
    }

    override fun onUltrasoundButtonClicked() {
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

    override fun onTestButtonClicked() {
        if (model?.bumperButton?.isSelected?.get() == true) {
            view?.showDialog(BumperTestDialog.Configure())
        }

        if (model?.ultrasoundButton?.isSelected?.get() == true) {
            view?.showDialog(UltrasonicTestDialog.Configure())
        }
    }

    override fun onDoneButtonClicked() {
        sensor?.apply {
            type =
                if (model?.bumperButton?.isSelected?.get() == true) {
                    Sensor.TYPE_BUMPER
                } else if (model?.ultrasoundButton?.isSelected?.get() == true) {
                    Sensor.TYPE_ULTRASONIC
                } else {
                    null
                }
            configurationEventBus.publishSensorUpdateEvent(SensorPort(this, portName))
        }
    }

    private fun Sensor?.isTestable() =
        this?.type == Sensor.TYPE_BUMPER || this?.type == Sensor.TYPE_ULTRASONIC
}
