package com.revolution.robotics.blockly.dialogs.textInput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogTextInputBinding
import com.revolution.robotics.views.ChippedEditTextViewModel
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogButtonHelper

class TextInputDialog : JavascriptPromptDialog<BlocklyDialogTextInputBinding>(R.layout.blockly_dialog_text_input) {

    companion object {

        private const val MAX_LENGTH = 40

        private var Bundle.title by BundleArgumentDelegate.String("title")
        private var Bundle.defaultValue by BundleArgumentDelegate.StringNullable("defaultValue")

        fun newInstance(title: String, defaultValue: String? = null) =
            TextInputDialog().withArguments { bundle ->
                bundle.title = title
                bundle.defaultValue = defaultValue
            }
    }

    override val hasCloseButton = true
    override val hasTitle = true

    private val dialogButtonHelper = DialogButtonHelper()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            dialogButtonHelper.createButtons(binding.buttonContainer, setOf(
                DialogButton(R.string.cancel, R.drawable.ic_close) {
                    dismissAllowingStateLoss()
                },
                DialogButton(R.string.done, R.drawable.ic_check, true) {
                    confirmPromptResult(binding.text.getContent())
                }
            ))
            title.set(arguments?.title)
            binding.text.setViewModel(
                ChippedEditTextViewModel(
                    title = arguments?.title,
                    text = arguments?.defaultValue,
                    titleColor = R.color.white,
                    hintColor = R.color.grey_8e,
                    borderColor = R.color.grey_8e,
                    backgroundColor = R.color.grey_28,
                    textMaxLength = MAX_LENGTH
                )
            )
        }
}
