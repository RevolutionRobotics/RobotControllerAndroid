package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class MotorConfigurationPresenter(private val resourceResolver: ResourceResolver) : MotorConfigurationMvp.Presenter {
    override var view: MotorConfigurationMvp.View? = null
    override var model: MotorConfigurationViewModel? = null

    override fun setMotor(motor: Motor, portName: String) {
        model?.editTextTitle?.value = "$portName${resourceResolver.string(R.string.config_name_edit_title)}"
        when (motor.type) {
            "motor" -> onMotorClicked()
            "drivetrain" -> onDrivetrainButtonClicked()
            else -> onEmptyButtonClicked()
        }
        when (motor.side) {
            "left" -> onLeftSideClicked()
            "right" -> onRightSideClicked()
        }
        when (motor.rotation) {
            "counterClockwise" -> onCounterClockwiseClicked()
            "clockwise" -> onClockwiseClicked()
        }
    }

    override fun onEmptyButtonClicked() {
        model?.apply {
            emptyButton.isSelected.set(true)
            motorButton.isSelected.set(false)
            driveTrainButton.isSelected.set(false)

            sideLeftButton.isVisible.set(false)
            sideRightButton.isVisible.set(false)
            counterClockwiseButton.isVisible.set(false)
            clockwiseButton.isVisible.set(false)
        }
    }

    override fun onDrivetrainButtonClicked() {
        model?.apply {
            emptyButton.isSelected.set(false)
            motorButton.isSelected.set(false)
            driveTrainButton.isSelected.set(true)

            sideLeftButton.isVisible.set(true)
            sideRightButton.isVisible.set(true)
            counterClockwiseButton.isVisible.set(true)
            clockwiseButton.isVisible.set(true)
        }
    }

    override fun onMotorClicked() {
        model?.apply {
            emptyButton.isSelected.set(false)
            motorButton.isSelected.set(true)
            driveTrainButton.isSelected.set(false)

            sideLeftButton.isVisible.set(true)
            sideRightButton.isVisible.set(true)
            counterClockwiseButton.isVisible.set(false)
            clockwiseButton.isVisible.set(false)
        }
    }

    override fun onLeftSideClicked() {
        model?.apply {
            sideLeftButton.isSelected.set(true)
            sideRightButton.isSelected.set(false)
        }
    }

    override fun onRightSideClicked() {
        model?.apply {
            sideLeftButton.isSelected.set(false)
            sideRightButton.isSelected.set(true)
        }
    }

    override fun onCounterClockwiseClicked() {
        model?.apply {
            counterClockwiseButton.isSelected.set(true)
            clockwiseButton.isSelected.set(false)
        }
    }

    override fun onClockwiseClicked() {
        model?.apply {
            counterClockwiseButton.isSelected.set(false)
            clockwiseButton.isSelected.set(true)
        }
    }
}