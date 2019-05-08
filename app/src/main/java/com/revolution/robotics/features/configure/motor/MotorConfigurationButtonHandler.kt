package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

@Suppress("TooManyFunctions")
class MotorConfigurationButtonHandler(private val model: MotorConfigurationViewModel) {
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

    fun initDrivetrain(motor: Motor) {
        model.apply {
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

            setDoneButton(true)
            setTestButton(true)
        }
    }

    fun initMotor(motor: Motor) {
        model.apply {
            clearVisibilitiesAndSelections()
            motorButton.isSelected.set(true)

            motorClockwiseButton.isVisible.set(true)
            motorClockwiseButton.isSelected.set(motor.rotation == Motor.ROTATION_CLOCKWISE)
            motorCounterClockwiseButton.isVisible.set(true)
            motorCounterClockwiseButton.isSelected.set(motor.rotation != Motor.ROTATION_CLOCKWISE)

            setDoneButton(true)
            setTestButton(true)
        }
    }

    fun onEmptyButtonClicked() {
        model.apply {
            clearVisibilitiesAndSelections()
            emptyButton.isSelected.set(true)
            setDoneButton(true)
            setTestButton(false)
        }
    }

    fun onDrivetrainButtonClicked() {
        model.apply {
            clearVisibilitiesAndSelections()
            driveTrainButton.isSelected.set(true)

            sideLeftButton.isVisible.set(true)
            sideLeftButton.isSelected.set(false)
            sideRightButton.isSelected.set(false)
            sideRightButton.isVisible.set(true)

            setDoneButton(false)
            setTestButton(false)
        }
    }

    fun onMotorClicked() {
        model.apply {
            clearVisibilitiesAndSelections()
            motorButton.isSelected.set(true)

            motorClockwiseButton.isSelected.set(false)
            motorClockwiseButton.isVisible.set(true)
            motorCounterClockwiseButton.isSelected.set(false)
            motorCounterClockwiseButton.isVisible.set(true)

            setDoneButton(false)
            setTestButton(false)
        }
    }

    fun onLeftSideClicked() {
        model.apply {
            sideLeftButton.isSelected.set(true)
            sideRightButton.isSelected.set(false)

            counterClockwiseButton.isVisible.set(true)
            counterClockwiseButton.isSelected.set(true)

            clockwiseButton.isVisible.set(true)
            clockwiseButton.isSelected.set(false)

            setDoneButton(true)
            setTestButton(true)
        }
    }

    fun onRightSideClicked() {
        model.apply {
            sideLeftButton.isSelected.set(false)
            sideRightButton.isSelected.set(true)

            counterClockwiseButton.isVisible.set(true)
            counterClockwiseButton.isSelected.set(false)

            clockwiseButton.isVisible.set(true)
            clockwiseButton.isSelected.set(true)

            setDoneButton(true)
            setTestButton(true)
        }
    }

    fun onCounterClockwiseClicked() {
        model.apply {
            if (motorButton.isSelected.get()) {
                motorClockwiseButton.isSelected.set(false)
                motorCounterClockwiseButton.isSelected.set(true)

                setDoneButton(true)
                setTestButton(true)
            } else {
                counterClockwiseButton.isSelected.set(true)
                clockwiseButton.isSelected.set(false)
            }
        }
    }

    fun onClockwiseClicked() {
        model.apply {
            if (motorButton.isSelected.get()) {
                motorClockwiseButton.isSelected.set(true)
                motorCounterClockwiseButton.isSelected.set(false)

                setDoneButton(true)
                setTestButton(true)
            } else {
                counterClockwiseButton.isSelected.set(false)
                clockwiseButton.isSelected.set(true)
            }
        }
    }

    private fun clearVisibilitiesAndSelections() {
        model.apply {
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

    private fun setDoneButton(enabled: Boolean) {
        model.apply {
            actionButtonsViewModel.doneButtonEnabled.set(enabled)
            if (enabled) {
                actionButtonsViewModel.doneButtonChippedBoxConfig.value = chippedConfigDoneEnabled
                actionButtonsViewModel.doneTextColor.set(R.color.white)
            } else {
                actionButtonsViewModel. doneButtonChippedBoxConfig.value = chippedConfigDoneDisabled
                actionButtonsViewModel.doneTextColor.set(R.color.grey_8e)
            }
        }
    }

    private fun setTestButton(enabled: Boolean) {
        model.apply {
            actionButtonsViewModel.testButtonEnabled.set(enabled)
            actionButtonsViewModel.testTextColor.set(if (enabled) R.color.white else R.color.grey_8e)
        }
    }
}
