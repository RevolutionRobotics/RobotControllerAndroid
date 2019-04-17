package com.revolution.robotics.blockly.dialogs.text

import android.os.Bundle
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogInputTextBinding
import org.revolution.blockly.view.DialogFactory

class TextInputDialog : JavascriptPromptDialog<BlocklyDialogInputTextBinding>(R.layout.blockly_dialog_input_text) {

    companion object {

        private var Bundle.options by BundleArgumentDelegate.Parcelable<DialogFactory.TextOptions>("options")

        fun newInstance(options: DialogFactory.TextOptions) =
            TextInputDialog().withArguments { bundle ->
                bundle.options = options
            }
    }

    override fun getResult() = "${binding.input.text}"
}
