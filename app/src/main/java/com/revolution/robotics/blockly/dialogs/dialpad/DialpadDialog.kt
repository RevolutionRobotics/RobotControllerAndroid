package com.revolution.robotics.blockly.dialogs.dialpad

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogDialpadBinding

class DialpadDialog : JavascriptPromptDialog<BlocklyDialogDialpadBinding>(R.layout.blockly_dialog_dialpad) {

    companion object {

        private var Bundle.hasDefaultValue by BundleArgumentDelegate.Boolean("has-default-value")
        private var Bundle.defaultValue by BundleArgumentDelegate.Double("default-value")

        fun newInstance(defaultValue: Double? = null) = DialpadDialog().withArguments { bundle ->
            bundle.hasDefaultValue = defaultValue != null
            bundle.defaultValue = defaultValue ?: 0.0
        }
    }

    override val hasCloseButton = false
    override val hasTitle = false

    override fun dialogWidth() = R.dimen.dialog_dialpad_width

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = DialpadViewModel(this)
        (binding.root as ConstraintLayout).children.forEach { child ->
            if (child is TextView && child.tag != null) {
                child.text = "${child.tag}"
                child.setOnClickListener { binding.viewModel?.onCharacterClicked(child.tag as String) }
            }
        }

        arguments?.let { arguments ->
            if (arguments.hasDefaultValue) {
                if (arguments.defaultValue == 0.0) {
                    binding.viewModel?.result?.set("0") // avoid writing 0.0
                } else {
                    binding.viewModel?.result?.set("${arguments.defaultValue}")
                }
            }
        }
    }
}
