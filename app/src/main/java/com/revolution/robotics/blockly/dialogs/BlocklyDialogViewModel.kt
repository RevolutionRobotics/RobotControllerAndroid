package com.revolution.robotics.blockly.dialogs

import android.app.Dialog
import android.webkit.JsPromptResult
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.core.extensions.createAndShowDialog

abstract class BlocklyDialogViewModel<T : ViewDataBinding>(
    private val jsResult: JsPromptResult,
    protected val binding: T
) {

    private var dialog: Dialog? = null

    fun createAndShowDialog() {
        val view = binding.root
        dialog = view.context.createAndShowDialog(view)
    }

    fun onCancelClicked() {
        dialog?.dismiss()
        jsResult.cancel()
    }

    fun onOkClicked() {
        val result = getResult()
        dialog?.dismiss()
        jsResult.confirm(result)
    }

    abstract fun getResult(): String

}