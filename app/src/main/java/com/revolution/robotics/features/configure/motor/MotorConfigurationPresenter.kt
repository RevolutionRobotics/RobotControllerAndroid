package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.interactor.GetUserConfigurationInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.build.testing.DrivetrainTestDialog
import com.revolution.robotics.features.build.testing.MotorTestDialog
import com.revolution.robotics.features.build.testing.TestDialog
import com.revolution.robotics.features.configure.ConfigurationEventBus
import com.revolution.robotics.features.configure.ConfigureFragment
import com.revolution.robotics.features.configure.MotorPort
import com.revolution.robotics.features.shared.ErrorHandler
import com.revolution.robotics.views.ChippedEditTextViewModel

@Suppress("ComplexInterface", "TooManyFunctions")
class MotorConfigurationPresenter(
    private val resourceResolver: ResourceResolver,
    private val configurationEventBus: ConfigurationEventBus,
    private val getUserConfigurationInteractor: GetUserConfigurationInteractor,
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler
) : MotorConfigurationMvp.Presenter {

    override var view: MotorConfigurationMvp.View? = null
    override var model: MotorConfigurationViewModel? = null

    private var buttonHandler: MotorConfigurationButtonHandler? = null
    private var configId: Int = -1
    private var motor: Motor? = null
    private var portName: String? = null
    private var variableName: String? = null

    override fun register(view: MotorConfigurationMvp.View, model: MotorConfigurationViewModel?) {
        super.register(view, model)

        model?.let {
            buttonHandler = MotorConfigurationButtonHandler(it)
        }
    }

    override fun onVariableNameChanged(name: String?) {
        variableName = name
        buttonHandler?.setVariableName(name)
    }

    override fun setMotor(configId: Int, motor: Motor, portName: String) {
        this.motor = motor
        this.portName = portName
        this.configId = configId
        model?.editTextModel?.value = ChippedEditTextViewModel(
            title = "$portName - ${resourceResolver.string(R.string.configure_motor_name_inputfield_title)}",
            text = motor.variableName,
            borderColor = R.color.grey_8e,
            backgroundColor = R.color.grey_28,
            textColor = R.color.white,
            titleColor = R.color.white,
            digits = ConfigureFragment.ALLOWED_DIGITS_REGEXP
        )

        val portNumber = portName.substring(1, 2).toInt()
        when (motor.type) {
            Motor.TYPE_MOTOR -> buttonHandler?.initMotor(motor, portNumber)
            Motor.TYPE_DRIVETRAIN -> buttonHandler?.initDrivetrain(motor, portNumber)
            else -> buttonHandler?.initEmptyState(portNumber)
        }
    }

    override fun onEmptyButtonClicked() {
        buttonHandler?.onEmptyButtonClicked()
    }

    override fun onDrivetrainButtonClicked() {
        buttonHandler?.onDrivetrainButtonClicked()
    }

    override fun onMotorClicked() {
        buttonHandler?.onMotorClicked()
    }

    override fun onLeftSideClicked() {
        buttonHandler?.onLeftSideClicked()
    }

    override fun onRightSideClicked() {
        buttonHandler?.onRightSideClicked()
    }

    override fun onCounterClockwiseClicked() {
        buttonHandler?.onCounterClockwiseClicked()
    }

    override fun onClockwiseClicked() {
        buttonHandler?.onClockwiseClicked()
    }

    override fun onTestButtonClicked() {
        if (bluetoothManager.isConnected) {
            getUserConfigurationInteractor.userConfigId = configId
            getUserConfigurationInteractor.execute { userConfiguration ->
                if (model?.driveTrainButton?.isSelected?.get() == true && userConfiguration?.mappingId?.getMotorPortIndex(
                        portName
                    ) != null
                ) {
                    view?.showDialog(generateDriveTrainDialog(userConfiguration.mappingId?.getMotorPortIndex(portName)!!))
                }

                if (model?.motorButton?.isSelected?.get() == true && userConfiguration?.mappingId?.getMotorPortIndex(
                        portName
                    ) != null
                ) {
                    view?.showDialog(generateMotorDialog(userConfiguration.mappingId?.getMotorPortIndex(portName)!!))
                }
            }
        } else {
            bluetoothManager.startConnectionFlow()
        }
    }

    private fun generateDriveTrainDialog(portIndex: Int) = DrivetrainTestDialog.newInstance(
        (portIndex).toString(),
        if (model?.clockwiseButton?.isSelected?.get() == true) {
            TestDialog.VALUE_CLOCKWISE
        } else {
            TestDialog.VALUE_COUNTER_CLOCKWISE
        },
        if (model?.sideLeftButton?.isSelected?.get() == true) {
            TestDialog.VALUE_SIDE_LEFT
        } else {
            TestDialog.VALUE_SIDE_RIGHT
        }
    )

    private fun generateMotorDialog(portIndex: Int) = MotorTestDialog.newInstance(
        (portIndex).toString(),
        if (model?.motorClockwiseButton?.isSelected?.get() == true) {
            TestDialog.VALUE_CLOCKWISE
        } else {
            TestDialog.VALUE_COUNTER_CLOCKWISE
        }
    )

    private fun setDrivetrainValues(motor: Motor) {
        motor.apply {
            type = Motor.TYPE_DRIVETRAIN
            rotation =
                if (model?.clockwiseButton?.isSelected?.get() == true) {
                    Motor.DIRECTION_CLOCKWISE
                } else {
                    Motor.DIRECTION_COUNTER_CLOCKWISE
                }

            side =
                if (model?.sideLeftButton?.isSelected?.get() == true) {
                    Motor.SIDE_LEFT
                } else {
                    Motor.SIDE_RIGHT
                }
            variableName = this@MotorConfigurationPresenter.variableName
        }
    }

    private fun setMotorValues(motor: Motor) {
        motor.apply {
            type = Motor.TYPE_MOTOR
            rotation =
                if (model?.motorClockwiseButton?.isSelected?.get() == true) {
                    Motor.DIRECTION_CLOCKWISE
                } else {
                    Motor.DIRECTION_COUNTER_CLOCKWISE
                }
            side = null
            variableName = this@MotorConfigurationPresenter.variableName
        }
    }

    private fun setEmptyValues(motor: Motor) {
        motor.apply {
            type = null
            rotation = null
        }
    }

    override fun onDoneButtonClicked() {
        getUserConfigurationInteractor.userConfigId = configId
        getUserConfigurationInteractor.execute { userConfiguration ->
            motor?.apply {
                if (userConfiguration?.mappingId != null && userConfiguration.mappingId!!.isUsedVariableName(
                        this@MotorConfigurationPresenter.variableName ?: "",
                        portName ?: ""
                    )
                ) {
                    errorHandler.onError(R.string.error_variable_already_in_use)
                } else {
                    when {
                        model?.driveTrainButton?.isSelected?.get() == true -> setDrivetrainValues(this)
                        model?.motorButton?.isSelected?.get() == true -> setMotorValues(this)
                        else -> setEmptyValues(this)
                    }

                    if (model?.emptyButton?.isSelected?.get() == true) {
                        variableName = null
                        configurationEventBus.publishMotorUpdateEvent(MotorPort(null, portName))
                    } else {
                        variableName = this@MotorConfigurationPresenter.variableName
                        configurationEventBus.publishMotorUpdateEvent(MotorPort(this, portName))
                    }
                }
            }
        }
    }
}
