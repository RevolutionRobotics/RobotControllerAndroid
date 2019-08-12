package com.revolution.robotics.features.configure.sensor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.interactor.GetUserConfigurationInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.build.testing.BumperTestDialog
import com.revolution.robotics.features.build.testing.UltrasonicTestDialog
import com.revolution.robotics.features.configure.ConfigurationEventBus
import com.revolution.robotics.features.configure.ConfigureFragment
import com.revolution.robotics.features.configure.SensorPort
import com.revolution.robotics.features.shared.ErrorHandler
import com.revolution.robotics.views.ChippedEditTextViewModel
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class SensorConfigurationPresenter(
    private val resourceResolver: ResourceResolver,
    private val configurationEventBus: ConfigurationEventBus,
    private val getUserConfigurationInteractor: GetUserConfigurationInteractor,
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler
) : SensorConfigurationMvp.Presenter {

    override var view: SensorConfigurationMvp.View? = null
    override var model: SensorConfigurationViewModel? = null

    private var configId: Int = -1
    private var portName: String? = null
    private var sensor: Sensor? = null
    private var variableName: String? = null

    private var previousUltrasonicName: String? = null
    private var previousBumperName: String? = null

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
        .borderColorResource(R.color.grey_1d)
        .chipTopRight(true)
        .chipBottomLeft(false)
        .chipBottomRight(false)
        .chipTopLeft(false)
        .chipSize(R.dimen.dimen_8dp)
        .borderSize(R.dimen.dimen_1dp)
        .create()

    override fun setSensor(configId: Int, sensor: Sensor, portName: String) {
        this.configId = configId
        this.portName = portName
        this.sensor = sensor
        previousUltrasonicName = null
        previousBumperName = null

        model?.apply {
            editTextModel.value = ChippedEditTextViewModel(
                title = "$portName - ${resourceResolver.string(R.string.configure_motor_name_inputfield_title)}",
                text = sensor.variableName,
                borderColor = R.color.grey_8e,
                backgroundColor = R.color.grey_28,
                textColor = R.color.white,
                titleColor = R.color.white,
                digits = ConfigureFragment.ALLOWED_DIGITS_REGEXP,
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

            if (bumperButton.isSelected.get()) {
                previousBumperName = variableName
            }

            if (ultrasoundButton.isSelected.get()) {
                previousUltrasonicName = variableName
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
            getUserConfigurationInteractor.userConfigId = configId
            getUserConfigurationInteractor.execute { userConfiguration ->
                editTextModel.value = editTextModel.value?.apply {
                    enabled = true
                }
                if (ultrasoundButton.isSelected.get()) {
                    previousUltrasonicName = variableName
                }
                bumperButton.isSelected.set(true)
                ultrasoundButton.isSelected.set(false)
                emptyButton.isSelected.set(false)
                setTestButton(true)
                val newVariableName = previousBumperName ?: userConfiguration?.mappingId?.getDefaultBumperName()
                editTextModel.value?.text = newVariableName
                onVariableNameChanged(newVariableName)
            }
        }
    }

    override fun onUltrasoundButtonClicked() {
        model?.apply {
            getUserConfigurationInteractor.userConfigId = configId
            getUserConfigurationInteractor.execute { userConfiguration ->
                editTextModel.value = editTextModel.value?.apply {
                    enabled = true
                }
                if (bumperButton.isSelected.get()) {
                    previousBumperName = variableName
                }
                bumperButton.isSelected.set(false)
                ultrasoundButton.isSelected.set(true)
                emptyButton.isSelected.set(false)
                setTestButton(true)
                val newVariableName = previousUltrasonicName ?: userConfiguration?.mappingId?.getDefaultUltrasonicName()
                editTextModel.value?.text = newVariableName
                onVariableNameChanged(newVariableName)
            }

        }
    }

    private fun setTestButton(enabled: Boolean) {
        model?.apply {
            actionButtonsViewModel.testButtonEnabled.set(enabled)
            actionButtonsViewModel.testTextColor.set(if (enabled) R.color.white else R.color.grey_8e)
        }
    }

    override fun onTestButtonClicked() {
        if (bluetoothManager.isConnected) {
            getUserConfigurationInteractor.userConfigId = configId
            getUserConfigurationInteractor.execute { userConfiguration ->
                if (model?.bumperButton?.isSelected?.get() == true) {
                    view?.showDialog(
                        BumperTestDialog.newInstance(
                            (userConfiguration?.mappingId?.getSensorPortIndex(portName)
                                ?: 0).toString()
                        )
                    )
                }

                if (model?.ultrasoundButton?.isSelected?.get() == true) {
                    view?.showDialog(
                        UltrasonicTestDialog.newInstance(
                            (userConfiguration?.mappingId?.getSensorPortIndex(portName)
                                ?: 0).toString()
                        )
                    )
                }
            }

        } else {
            bluetoothManager.startConnectionFlow()
        }
    }

    override fun onDoneButtonClicked() {
        getUserConfigurationInteractor.userConfigId = configId
        getUserConfigurationInteractor.execute { userConfiguration ->
            sensor?.apply {
                val isUsedVariable =
                    userConfiguration?.mappingId?.isUsedVariableName(
                        this@SensorConfigurationPresenter.variableName ?: "",
                        portName ?: ""
                    ) ?: false
                if (isUsedVariable) {
                    errorHandler.onError(R.string.error_variable_already_in_use)
                } else {
                    type = when {
                        model?.bumperButton?.isSelected?.get() == true -> Sensor.TYPE_BUMPER
                        model?.ultrasoundButton?.isSelected?.get() == true -> Sensor.TYPE_ULTRASONIC
                        else -> null
                    }

                    if (model?.emptyButton?.isSelected?.get() == true) {
                        variableName = null
                        configurationEventBus.publishSensorUpdateEvent(SensorPort(null, portName))
                    } else {
                        variableName = this@SensorConfigurationPresenter.variableName
                        configurationEventBus.publishSensorUpdateEvent(SensorPort(this, portName))
                    }
                }
            }
        }

    }

    private fun Sensor?.isTestable() =
        this?.type == Sensor.TYPE_BUMPER || this?.type == Sensor.TYPE_ULTRASONIC
}
