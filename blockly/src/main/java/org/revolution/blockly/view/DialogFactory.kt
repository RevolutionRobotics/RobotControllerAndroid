package org.revolution.blockly.view

import android.content.Context
import android.webkit.JsPromptResult

interface DialogFactory {

    fun showTextInputDialog(result: JsPromptResult, options: TextOptions, context: Context)

    fun showSliderDialog(result: JsPromptResult, options: SliderOptions, context: Context)

    // Option classes

    data class TextOptions(val title: String)
    data class SliderOptions(val minValue: Int, val maxValue: Int)
}
