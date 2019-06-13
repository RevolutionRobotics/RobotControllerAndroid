package com.revolution.robotics.utils

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogConfirmBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

@Suppress("OptionalUnit")
abstract class ConfirmDialog(
    positiveButtonText: Int,
    positiveButtonIcon: Int = R.drawable.ic_check,
    negativeButtonText: Int = R.string.cancel,
    negativeButtonIcon: Int = R.drawable.ic_close
) : RoboticsDialog() {

    override val hasCloseButton = true
    override val dialogFaces = listOf(ConfirmDialogFace(this))
    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(negativeButtonText, negativeButtonIcon, false) {
            dismissAllowingStateLoss()
        },
        DialogButton(positiveButtonText, positiveButtonIcon, true) {
            onConfirmed()
            dismiss()
        }
    )

    open fun onActivated(binding: DialogConfirmBinding?) = Unit

    abstract fun onConfirmed()

    class ConfirmDialogFace(private val confirmDialog: ConfirmDialog) :
        DialogFace<DialogConfirmBinding>(R.layout.dialog_confirm, confirmDialog) {

        override fun onActivated() {
            confirmDialog.onActivated(binding)
        }
    }
}
