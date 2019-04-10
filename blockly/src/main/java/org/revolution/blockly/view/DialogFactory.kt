package org.revolution.blockly.view

import android.content.Context
import android.webkit.JsPromptResult

interface DialogFactory {

    fun showTextInputDialog(result: JsPromptResult, context: Context)

    fun showSliderDialog(result: JsPromptResult, context: Context)
}