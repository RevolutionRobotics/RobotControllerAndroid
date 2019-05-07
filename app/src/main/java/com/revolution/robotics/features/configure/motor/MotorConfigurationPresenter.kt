package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.kodein.utils.ResourceResolver

@Suppress("ComplexInterface")
class MotorConfigurationPresenter(private val resourceResolver: ResourceResolver) : MotorConfigurationMvp.Presenter {

    override var view: MotorConfigurationMvp.View? = null
    override var model: MotorConfigurationViewModel? = null

    private var buttonHandler: MotorConfigurationButtonHandler? = null

    override fun register(view: MotorConfigurationMvp.View, model: MotorConfigurationViewModel?) {
        super.register(view, model)
        model?.let {
            buttonHandler = MotorConfigurationButtonHandler(it)
        }
    }

    override fun setMotor(motor: Motor, portName: String) {
        model?.editTextTitle?.value =
            "$portName - ${resourceResolver.string(R.string.configure_motor_name_inputfield_title)}"
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

    override fun onTestButtonClcked() {
        // TODO Handle test button
    }

    override fun onDoneButtonClicked() {
        // TODO handle done button
    }
}