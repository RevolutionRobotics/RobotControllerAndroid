package com.revolution.robotics.features.configure.motor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

@Suppress("TooManyFunctions")
class MotorConfigurationButtonHandler(
    private val model: MotorConfigurationViewModel
) {
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

    private var variableName: String? = null
    private var previousMotorName: String? = null
    private var previousDrivetrainName: String? = null
    private var portNumber = 0

    fun setVariableName(name: String?) {
        variableName = name
        setDoneButton()
    }

    fun initDrivetrain(motor: Motor, portNumber: Int) {
        this.portNumber = portNumber
        model.apply {
            variableName = motor.variableName
            previousDrivetrainName = motor.variableName
            clearVisibilitiesAndSelections()
            driveTrainButton.isSelected.set(true)

            sideRightButton.isVisible.set(true)
            sideRightButton.isSelected.set(motor.side == Motor.SIDE_RIGHT)
            sideLeftButton.isVisible.set(true)
            sideLeftButton.isSelected.set(motor.side != Motor.SIDE_RIGHT)

            reversedCheckboxVisible.set(true)
            reversed.set(motor.reversed)

            setDoneButton()
            setTestButton(true)
        }
    }

    fun initMotor(motor: Motor, portNumber: Int) {
        this.portNumber = portNumber
        model.apply {
            variableName = motor.variableName
            previousMotorName = motor.variableName
            clearVisibilitiesAndSelections()
            motorButton.isSelected.set(true)

            setDoneButton()
            setTestButton(true)
        }
    }

    fun initEmptyState(portNumber: Int) {
        this.portNumber = portNumber
        onEmptyButtonClicked()
    }

    fun onEmptyButtonClicked() {
        model.apply {
            if (driveTrainButton.isSelected.get()) {
                previousDrivetrainName = variableName
            }
            if (motorButton.isSelected.get()) {
                previousMotorName = variableName
            }

            editTextModel.value = editTextModel.value?.apply {
                enabled = false
                text = ""
            }
            clearVisibilitiesAndSelections()
            emptyButton.isSelected.set(true)
            setDoneButton()
            setTestButton(false)
        }
    }

    fun onDrivetrainButtonClicked() {
        model.apply {
            editTextModel.value = editTextModel.value?.apply {
                enabled = true
            }
            if (motorButton.isSelected.get()) {
                previousMotorName = variableName
            }
            clearVisibilitiesAndSelections()
            driveTrainButton.isSelected.set(true)

            sideLeftButton.isVisible.set(true)
            sideLeftButton.isSelected.set(false)
            sideRightButton.isSelected.set(false)
            sideRightButton.isVisible.set(true)

            editTextModel.value?.text = previousDrivetrainName ?: "drive$portNumber"
            setDoneButton()
            setTestButton(false)
        }
    }

    fun onMotorClicked() {
        model.apply {
            editTextModel.value = editTextModel.value?.apply {
                enabled = true
            }
            if (driveTrainButton.isSelected.get()) {
                previousDrivetrainName = variableName
            }

            clearVisibilitiesAndSelections()
            motorButton.isSelected.set(true)

            reversed.set(false)
            reversedCheckboxVisible.set(false)

            editTextModel.value?.text = previousMotorName ?: "motor$portNumber"

            setDoneButton()
            setTestButton(false)
        }
    }

    fun onLeftSideClicked() {
        model.apply {
            sideLeftButton.isSelected.set(true)
            sideRightButton.isSelected.set(false)

            reversedCheckboxVisible.set(true)
            reversed.set(false)

            setDoneButton()
            setTestButton(true)
        }
    }

    fun onRightSideClicked() {
        model.apply {
            sideLeftButton.isSelected.set(false)
            sideRightButton.isSelected.set(true)

            reversedCheckboxVisible.set(true)
            reversed.set(false)

            setDoneButton()
            setTestButton(true)
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
            reversed.set(false)
            reversedCheckboxVisible.set(false)
        }
    }

    private fun setDoneButton() {
        model.apply {
            if (emptyStateValid() || motorStateValid() || drivetrainStateValid()) {
                actionButtonsViewModel.doneButtonEnabled.set(true)
                actionButtonsViewModel.doneButtonChippedBoxConfig.value = chippedConfigDoneEnabled
                actionButtonsViewModel.doneTextColor.set(R.color.white)
            } else {
                actionButtonsViewModel.doneButtonEnabled.set(false)
                actionButtonsViewModel.doneButtonChippedBoxConfig.value = chippedConfigDoneDisabled
                actionButtonsViewModel.doneTextColor.set(R.color.grey_8e)
            }
        }
    }

    private fun drivetrainStateValid() = model.driveTrainButton.isSelected.get() &&
            (model.sideLeftButton.isSelected.get() || model.sideRightButton.isSelected.get()) &&
            !variableName.isNullOrEmpty()

    private fun motorStateValid() = model.motorButton.isSelected.get() &&
            !variableName.isNullOrEmpty()

    private fun emptyStateValid() = model.emptyButton.isSelected.get()

    private fun setTestButton(enabled: Boolean) {
        model.apply {
            actionButtonsViewModel.testButtonEnabled.set(enabled)
            actionButtonsViewModel.testTextColor.set(if (enabled) R.color.white else R.color.grey_8e)
        }
    }
}
