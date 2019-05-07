package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class MotorConfigurationPresenter(private val resourceResolver: ResourceResolver) : MotorConfigurationMvp.Presenter {
    override var view: MotorConfigurationMvp.View? = null
    override var model: MotorConfigurationViewModel? = null

    override fun setMotor(motor: Motor, portName: String) {
        model?.editTextTitle?.value = "$portName ${resourceResolver.string(R.string.config_name_edit_title)}"
        when (motor.type) {
            Motor.TYPE_MOTOR -> initMotor(motor)
            Motor.TYPE_DRIVETRAIN -> initDrivetrain(motor)
            else -> onEmptyButtonClicked()
        }
    }

    private fun clearVisibilitiesAndSelections() {
        model?.apply {
            emptyButton.isSelected.set(false)
            motorButton.isSelected.set(false)
            driveTrainButton.isSelected.set(false)
            sideLeftButton.isSelected.set(false)
            sideLeftButton.isVisible.set(false)
            sideRightButton.isSelected.set(false)
            sideRightButton.isVisible.set(false)
            counterClockwiseButton.isSelected.set(false)
            counterClockwiseButton.isVisible.set(false)
            clockwiseButton.isSelected.set(false)
            clockwiseButton.isVisible.set(false)
            motorClockwiseButton.isSelected.set(false)
            motorClockwiseButton.isVisible.set(false)
            motorCounterClockwiseButton.isSelected.set(false)
            motorCounterClockwiseButton.isVisible.set(false)
        }
    }

    private fun initDrivetrain(motor: Motor) {
        model?.apply {
            clearVisibilitiesAndSelections()
            driveTrainButton.isSelected.set(true)

            sideRightButton.isVisible.set(true)
            sideRightButton.isSelected.set(motor.side == Motor.SIDE_RIGHT)
            sideLeftButton.isVisible.set(true)
            sideLeftButton.isSelected.set(motor.side != Motor.SIDE_RIGHT)

            clockwiseButton.isVisible.set(true)
            counterClockwiseButton.isVisible.set(true)
            clockwiseButton.isSelected.set(motor.rotation == Motor.ROTATION_CLOCKWISE)
            counterClockwiseButton.isSelected.set(motor.rotation != Motor.ROTATION_CLOCKWISE)
        }
    }

    private fun initMotor(motor: Motor) {
        model?.apply {
            clearVisibilitiesAndSelections()
            motorButton.isSelected.set(true)

            motorClockwiseButton.isVisible.set(true)
            motorClockwiseButton.isSelected.set(motor.rotation == Motor.ROTATION_CLOCKWISE)
            motorCounterClockwiseButton.isVisible.set(true)
            motorCounterClockwiseButton.isSelected.set(motor.rotation != Motor.ROTATION_CLOCKWISE)
        }
    }

    override fun onEmptyButtonClicked() {
        model?.apply {
            clearVisibilitiesAndSelections()
            emptyButton.isSelected.set(true)
        }
    }

    override fun onDrivetrainButtonClicked() {
        model?.apply {
            clearVisibilitiesAndSelections()
            driveTrainButton.isSelected.set(true)

            sideLeftButton.isVisible.set(true)
            sideLeftButton.isSelected.set(false)
            sideRightButton.isSelected.set(false)
            sideRightButton.isVisible.set(true)
        }
    }

    override fun onMotorClicked() {
        model?.apply {
            clearVisibilitiesAndSelections()
            motorButton.isSelected.set(true)

            motorClockwiseButton.isSelected.set(false)
            motorClockwiseButton.isVisible.set(true)
            motorCounterClockwiseButton.isSelected.set(false)
            motorCounterClockwiseButton.isVisible.set(true)
        }
    }

    override fun onLeftSideClicked() {
        model?.apply {
            sideLeftButton.isSelected.set(true)
            sideRightButton.isSelected.set(false)

            counterClockwiseButton.isVisible.set(true)
            counterClockwiseButton.isSelected.set(true)

            clockwiseButton.isVisible.set(true)
            clockwiseButton.isSelected.set(false)
        }
    }

    override fun onRightSideClicked() {
        model?.apply {
            sideLeftButton.isSelected.set(false)
            sideRightButton.isSelected.set(true)

            counterClockwiseButton.isVisible.set(true)
            counterClockwiseButton.isSelected.set(false)

            clockwiseButton.isVisible.set(true)
            clockwiseButton.isSelected.set(true)
        }
    }

    override fun onCounterClockwiseClicked() {
        model?.apply {
            if (motorButton.isSelected.get()) {
                motorClockwiseButton.isSelected.set(false)
                motorCounterClockwiseButton.isSelected.set(true)
            } else {
                counterClockwiseButton.isSelected.set(true)
                clockwiseButton.isSelected.set(false)
            }
        }
    }

    override fun onClockwiseClicked() {
        model?.apply {
            if (motorButton.isSelected.get()) {
                motorClockwiseButton.isSelected.set(true)
                motorCounterClockwiseButton.isSelected.set(false)
            } else {
                counterClockwiseButton.isSelected.set(false)
                clockwiseButton.isSelected.set(true)
            }
        }
    }
}