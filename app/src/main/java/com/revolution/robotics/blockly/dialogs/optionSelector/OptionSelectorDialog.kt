package com.revolution.robotics.blockly.dialogs.optionSelector

import android.os.Bundle
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogOptionSelectorBinding

class OptionSelectorDialog :
    JavascriptPromptDialog<BlocklyDialogOptionSelectorBinding>(R.layout.blockly_dialog_option_selector) {

    override val hasCloseButton = true
    override val hasTitle = true

    companion object {
        private var Bundle.title by BundleArgumentDelegate.String("title")
        private var Bundle.options by BundleArgumentDelegate.Parcelable<OptionList>("options")

        fun newInstance(title: String, options: List<Option>) =
            OptionSelectorDialog().withArguments { bundle ->
                bundle.title = title
                bundle.options = OptionList().apply { addAll(options) }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arguments ->
            binding.viewModel = OptionSelectorViewModel(arguments.options, blocklyResultHolder, this)
            title.set(arguments.title)
        }
    }
}
