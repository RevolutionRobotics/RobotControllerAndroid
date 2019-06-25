package com.revolution.robotics.blockly.dialogs.confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogConfirmBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogButtonHelper

class ConfirmDialog : JavascriptPromptDialog<BlocklyDialogConfirmBinding>(R.layout.blockly_dialog_confirm) {

    companion object {
        private var Bundle.isConfirmation by BundleArgumentDelegate.Boolean("isConfirmation")
        private var Bundle.message by BundleArgumentDelegate.String("message")

        fun newInstance(message: String, isConfirmation: Boolean) = ConfirmDialog().withArguments { bundle ->
            bundle.message = message
            bundle.isConfirmation = isConfirmation
        }
    }

    override val hasCloseButton = true
    override val hasTitle = false

    private val buttonHelper = DialogButtonHelper()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            buttonHelper.createButtons(binding.buttonContainer, getButtons())
            binding.description.text = arguments?.message
        }

    private fun getButtons() =
        listOfNotNull(
            if (arguments?.isConfirmation == true) {
                DialogButton(R.string.cancel, R.drawable.ic_close) {
                    dismissAllowingStateLoss()
                }
            } else {
                null
            },
            DialogButton(R.string.ok, R.drawable.ic_check, true) {
                confirmResult()
            }
        ).toSet()
}
