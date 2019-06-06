package com.revolution.robotics.features.coding.python

import android.os.Bundle
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.DialogPythonCodeBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class PythonDialog : RoboticsDialog() {

    companion object {
        private var Bundle.code by BundleArgumentDelegate.String("code")

        fun newInstance(code: String) = PythonDialog().withArguments {
            it.code = code
        }
    }

    override val hasCloseButton: Boolean = true
    override val dialogFaces: List<DialogFace<*>> = listOf(
        PythonDialogFace(this)
    )

    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(R.string.done, R.drawable.ic_check, true) {
            dismissAllowingStateLoss()
        }
    )

    class PythonDialogFace(roboticsDialog: RoboticsDialog) :
        DialogFace<DialogPythonCodeBinding>(R.layout.dialog_python_code, roboticsDialog) {

        override fun onActivated() {
            super.onActivated()
            binding?.txtCode?.text = dialog?.arguments?.code ?: ""
        }
    }
}
