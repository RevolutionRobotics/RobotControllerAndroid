package org.revolution.blockly.view

import android.webkit.JsPromptResult

interface DialogFactory {

    fun showTextInputDialog(result: JsPromptResult, options: TextOptions)

    fun showSliderDialog(result: JsPromptResult, options: SliderOptions)

    // Option classes

    data class TextOptions(val title: String)
    data class SliderOptions(val minValue: Int, val maxValue: Int)
}
