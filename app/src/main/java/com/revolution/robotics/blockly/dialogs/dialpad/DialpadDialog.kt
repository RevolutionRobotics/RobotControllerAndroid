package com.revolution.robotics.blockly.dialogs.dialpad

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.databinding.BlocklyDialogDialpadBinding

class DialpadDialog : JavascriptPromptDialog<BlocklyDialogDialpadBinding>(R.layout.blockly_dialog_dialpad) {

    companion object {
        fun newInstance() = DialpadDialog()
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
    }
}
