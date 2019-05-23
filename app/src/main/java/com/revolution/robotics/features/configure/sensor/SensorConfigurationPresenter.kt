package com.revolution.robotics.features.configure.sensor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.build.testing.BumperTestDialog
import com.revolution.robotics.features.build.testing.UltrasonicTestDialog
import com.revolution.robotics.features.configure.ConfigurationEventBus
import com.revolution.robotics.features.configure.SensorPort
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.views.ChippedEditTextViewModel
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class SensorConfigurationPresenter(
    private val resourceResolver: ResourceResolver,
    private val configurationEventBus: ConfigurationEventBus,
    private val userConfigurationStorage: UserConfigurationStorage
) : SensorConfigurationMvp.Presenter {

    override var view: SensorConfigurationMvp.View? = null
    override var model: SensorConfigurationViewModel? = null

    private var portName: String? = null
    private var sensor: Sensor? = null
    private var variableName: String? = null

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

    private val chippedConfigDoneDisabled = ChippedBoxConfig.Builder()
        .backgroundColorResource(R.color.grey_28)
        .borderColorResource(R.color.grey_1e)
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
                titleColor = R.color.white,
                digits = UserConfigurationStorage.ALLOWED_DIGITS_REGEXP,
                enabled = sensor.type == Sensor.TYPE_BUMPER || sensor.type == Sensor.TYPE_ULTRASONIC
            )

            setTestButton(sensor.isTestable())
            bumperButton.isSelected.set(sensor.type == Sensor.TYPE_BUMPER)
            ultrasoundButton.isSelected.set(sensor.type == Sensor.TYPE_ULTRASONIC)
            emptyButton.isSelected.set(sensor.type.isNullOrEmpty())
            onVariableNameChanged(sensor.variableName)
        }
    }

    override fun onVariableNameChanged(name: String?) {
        variableName = name
        if (!name.isNullOrEmpty() || model?.emptyButton?.isSelected?.get() == true) {
            model?.actionButtonsViewModel?.doneButtonChippedBoxConfig?.value = chippedConfigDoneEnabled
            model?.actionButtonsViewModel?.doneTextColor?.set(R.color.white)
            model?.actionButtonsViewModel?.doneButtonEnabled?.set(true)
        } else {
            model?.actionButtonsViewModel?.doneButtonChippedBoxConfig?.value = chippedConfigDoneDisabled
            model?.actionButtonsViewModel?.doneTextColor?.set(R.color.grey_8e)
            model?.actionButtonsViewModel?.doneButtonEnabled?.set(false)
        }
    }

    override fun onEmptyButtonClicked() {
        model?.apply {
            editTextModel.value = editTextModel.value?.apply {
                enabled = false
            }
            bumperButton.isSelected.set(false)
            ultrasoundButton.isSelected.set(false)
            emptyButton.isSelected.set(true)
            setTestButton(false)
            onVariableNameChanged(this@SensorConfigurationPresenter.variableName)
        }
    }

    override fun onBumperButtonClicked() {
        model?.apply {
            editTextModel.value = editTextModel.value?.apply {
                enabled = true
            }
            bumperButton.isSelected.set(true)
            ultrasoundButton.isSelected.set(false)
            emptyButton.isSelected.set(false)
            setTestButton(true)
            onVariableNameChanged(this@SensorConfigurationPresenter.variableName)
        }
    }

    override fun onUltrasoundButtonClicked() {
        model?.apply {
            editTextModel.value = editTextModel.value?.apply {
                enabled = true
            }
            bumperButton.isSelected.set(false)
            ultrasoundButton.isSelected.set(true)
            emptyButton.isSelected.set(false)
            setTestButton(true)
            onVariableNameChanged(this@SensorConfigurationPresenter.variableName)
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
            if (userConfigurationStorage.isUsedVariableName(
                    this@SensorConfigurationPresenter.variableName ?: "",
                    portName ?: ""
                )
            ) {
                view?.showError(resourceResolver.string(R.string.error_variable_already_in_use) ?: "")
            } else {
                type =
                    when {
                        model?.bumperButton?.isSelected?.get() == true -> Sensor.TYPE_BUMPER
                        model?.ultrasoundButton?.isSelected?.get() == true -> Sensor.TYPE_ULTRASONIC
                        else -> null
                    }
                variableName = if (model?.emptyButton?.isSelected?.get() == true) {
                    ""
                } else {
                    this@SensorConfigurationPresenter.variableName
                }
                configurationEventBus.publishSensorUpdateEvent(SensorPort(this, portName))
            }
        }
    }

    private fun Sensor?.isTestable() =
        this?.type == Sensor.TYPE_BUMPER || this?.type == Sensor.TYPE_ULTRASONIC
}
