package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.interactor.GetUserRobotInteractor
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
    private val getUserRobotInteractor: GetUserRobotInteractor,
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler,
    private val reporter: Reporter
) : MotorConfigurationMvp.Presenter {

    override var view: MotorConfigurationMvp.View? = null
    override var model: MotorConfigurationViewModel? = null

    private var buttonHandler: MotorConfigurationButtonHandler? = null
    private var robotId: Int = -1
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

    override fun setMotor(robotId: Int, motor: Motor, portName: String) {
        this.motor = motor
        this.portName = portName
        this.robotId = robotId
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

    override fun onReversedChanged(reversed: Boolean) {
        model?.reversed?.set(reversed)
    }

    override fun onTestButtonClicked() {
        if (bluetoothManager.isServiceDiscovered) {
            getUserRobotInteractor.robotId = robotId
            getUserRobotInteractor.execute { userRobot ->
                if (model?.driveTrainButton?.isSelected?.get() == true && userRobot?.configuration?.mappingId?.getMotorPortIndex(
                        portName
                    ) != null
                ) {
                    view?.showDialog(
                        generateDriveTrainDialog(
                            userRobot.configuration.mappingId?.getMotorPortIndex(
                                portName
                            )!!
                        )
                    )
                }

                if (model?.motorButton?.isSelected?.get() == true && userRobot?.configuration?.mappingId?.getMotorPortIndex(
                        portName
                    ) != null
                ) {
                    view?.showDialog(
                        generateMotorDialog(
                            userRobot.configuration.mappingId?.getMotorPortIndex(
                                portName
                            )!!
                        )
                    )
                }
            }
        } else {
            bluetoothManager.startConnectionFlow()
        }
    }

    private fun generateDriveTrainDialog(portIndex: Int) = DrivetrainTestDialog.newInstance(
        (portIndex).toString(),
        model?.reversed?.get() == true,
        if (model?.sideLeftButton?.isSelected?.get() == true) {
            TestDialog.VALUE_SIDE_LEFT
        } else {
            TestDialog.VALUE_SIDE_RIGHT
        }
    )

    private fun generateMotorDialog(portIndex: Int) =
        MotorTestDialog.newInstance((portIndex).toString())

    private fun setDrivetrainValues(motor: Motor) {
        motor.apply {
            type = Motor.TYPE_DRIVETRAIN
            reversed = model?.reversed?.get() ?: false

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
            side = null
            variableName = this@MotorConfigurationPresenter.variableName
        }
    }

    private fun setEmptyValues(motor: Motor) {
        motor.apply {
            type = null
            reversed = false
        }
    }

    override fun onDoneButtonClicked() {
        getUserRobotInteractor.robotId = robotId
        getUserRobotInteractor.execute { userRobot ->
            motor?.apply {
                if (userRobot?.configuration?.mappingId != null && userRobot.configuration.mappingId!!.isUsedVariableName(
                        this@MotorConfigurationPresenter.variableName ?: "",
                        portName ?: ""
                    )
                ) {
                    errorHandler.onError(customMessage = R.string.error_variable_already_in_use)
                } else {
                    when {
                        model?.driveTrainButton?.isSelected?.get() == true -> {
                            setDrivetrainValues(this)
                            reporter.reportEvent(Reporter.Event.ADD_MOTOR)
                        }
                        model?.motorButton?.isSelected?.get() == true -> {
                            setMotorValues(this)
                            reporter.reportEvent(Reporter.Event.ADD_MOTOR)
                        }
                        else -> {
                            setEmptyValues(this)
                            reporter.reportEvent(Reporter.Event.REMOVE_MOTOR)
                        }
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
