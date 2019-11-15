package com.revolution.robotics.features.configure.save

import com.revolution.robotics.R
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog

abstract class SaveDialog : RoboticsDialog() {
    abstract val dialogFace: SaveDialogFace
    override val hasCloseButton = true
    private val doneButton = DialogButton(
        text = R.string.save_dialog_done,
        icon = R.drawable.ic_check,
        isHighlighted = true,
        isEnabledOnStart = false,
        onClick = { onDoneClicked() }
    )
    override val dialogButtons = listOf(doneButton)

    abstract fun onDoneClicked()

    protected fun disableDoneButton() {
        doneButton.viewModel?.isEnabled?.set(false)
    }

    protected fun enableDoneButton() {
        doneButton.viewModel?.isEnabled?.set(true)
    }
}
