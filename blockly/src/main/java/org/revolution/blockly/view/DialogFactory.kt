package org.revolution.blockly.view

import android.webkit.JsPromptResult
import org.revolution.blockly.BlocklyOption
import org.revolution.blockly.BlocklyVariable

@Suppress("ComplexInterface")
interface DialogFactory {

    fun showDirectionSelectorDialog(defaultValue: String, result: JsPromptResult)
    fun showSlider(title: String, maxValue: Int, defaultValue: Int, result: JsPromptResult)
    fun showDialpad(defaultValue: Double, result: JsPromptResult)
    fun showColorPicker(title: String, colors: List<String>, defaultValue: String, result: JsPromptResult)
    fun showSoundPicker(title: String, defaultValue: String?, result: JsPromptResult)
    fun showBlockOptionsDialog(title: String, comment: String, result: JsPromptResult)
    fun showTextInput(title: String, defaultValue: String?, result: JsPromptResult)
    fun showDonutSelector(defaultValue: String, isMultiSelection: Boolean, result: JsPromptResult)

    fun showOptionSelector(
        title: String,
        blocklyOptions: List<BlocklyOption>,
        defaultValue: BlocklyOption?,
        result: JsPromptResult
    )

    fun showVariableOptionsDialog(
        title: String,
        selectedKey: String,
        variables: List<BlocklyVariable>,
        result: JsPromptResult
    )
}
