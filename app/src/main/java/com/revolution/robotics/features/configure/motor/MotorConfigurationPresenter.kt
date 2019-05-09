package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.build.testing.DrivetrainTestDialog
import com.revolution.robotics.features.build.testing.MotorTestDialog
import com.revolution.robotics.features.configure.ConfigurationEventBus
import com.revolution.robotics.features.configure.MotorPort
import com.revolution.robotics.views.ChippedEditTextViewModel

@Suppress("ComplexInterface", "TooManyFunctions")
class MotorConfigurationPresenter(
    private val resourceResolver: ResourceResolver,
    private val configurationEventBus: ConfigurationEventBus
) : MotorConfigurationMvp.Presenter {

    override var view: MotorConfigurationMvp.View? = null
    override var model: MotorConfigurationViewModel? = null

    private var buttonHandler: MotorConfigurationButtonHandler? = null
    private var motor: Motor? = null
    private var portName: String? = null

    override fun register(view: MotorConfigurationMvp.View, model: MotorConfigurationViewModel?) {
        super.register(view, model)
        model?.let {
            buttonHandler = MotorConfigurationButtonHandler(it)
        }
    }

    override fun onVariableNameChanged(name: String?) {
        motor?.variableName = name
    }

    override fun setMotor(motor: Motor, portName: String) {
        this.motor = motor
        this.portName = portName
        model?.editTextModel?.value = ChippedEditTextViewModel(
            title = "$portName - ${resourceResolver.string(R.string.configure_motor_name_inputfield_title)}",
            text = motor.variableName,
            borderColor = R.color.grey_8e,
            backgroundColor = R.color.grey_28,
            textColor = R.color.white,
            titleColor = R.color.white
        )

        when (motor.type) {
            Motor.TYPE_MOTOR -> buttonHandler?.initMotor(motor)
            Motor.TYPE_DRIVETRAIN -> buttonHandler?.initDrivetrain(motor)
            else -> buttonHandler?.onEmptyButtonClicked()
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
        if (model?.driveTrainButton?.isSelected?.get() == true) {
            view?.showDialog(DrivetrainTestDialog.Configure())
        }

        if (model?.motorButton?.isSelected?.get() == true) {
            view?.showDialog(MotorTestDialog.Configure())
        }
    }

    private fun setDrivetrainValues(motor: Motor) {
        motor.apply {
            type = Motor.TYPE_DRIVETRAIN
            rotation =
                if (model?.clockwiseButton?.isSelected?.get() == true) {
                    Motor.ROTATION_CLOCKWISE
                } else {
                    Motor.ROTATION_COUNTER_CLOCKWISE
                }

            side =
                if (model?.sideLeftButton?.isSelected?.get() == true) {
                    Motor.SIDE_LEFT
                } else {
                    Motor.SIDE_RIGHT
                }
        }
    }

    private fun setMotorValues(motor: Motor) {
        motor.apply {
            type = Motor.TYPE_MOTOR
            rotation =
                if (model?.motorClockwiseButton?.isSelected?.get() == true) {
                    Motor.ROTATION_CLOCKWISE
                } else {
                    Motor.ROTATION_COUNTER_CLOCKWISE
                }
            side = null
        }
    }

    private fun setEmptyValues(motor: Motor) {
        motor.apply {
            type = null
            rotation = null
        }
    }

    override fun onDoneButtonClicked() {
        motor?.apply {
            when {
                model?.driveTrainButton?.isSelected?.get() == true -> setDrivetrainValues(this)
                model?.motorButton?.isSelected?.get() == true -> setMotorValues(this)
                else -> setEmptyValues(this)
            }
            configurationEventBus.publishMotorUpdateEvent(MotorPort(this, portName))
        }
    }
}
