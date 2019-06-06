package org.revolution.blockly.view

import android.webkit.JsPromptResult
import org.revolution.blockly.BlocklyOption

interface DialogFactory {

    fun showDirectionSelectorDialog(defaultValue: String, result: JsPromptResult)
    fun showSlider(title: String, maxValue: Int, defaultValue: Int, result: JsPromptResult)
    fun showOptionSelector(
        title: String,
        blocklyOptions: List<BlocklyOption>,
        default: BlocklyOption?,
        result: JsPromptResult
    )
    fun showColorPicker(title: String, colors: List<String>, selectedColor: String, result: JsPromptResult)
    fun showSoundPicker(title: String, selectedSound: String?, result: JsPromptResult)
    fun showBlockOptionsDialog(title: String, comment: String, result: JsPromptResult)
    fun showTextInput(title: String, defaultValue: String?, result: JsPromptResult)
    fun showDonutSelector(defaultSelection: String, isMultiSelection: Boolean, result: JsPromptResult)
}
