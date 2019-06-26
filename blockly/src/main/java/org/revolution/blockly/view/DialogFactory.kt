package org.revolution.blockly.view

import org.revolution.blockly.BlocklyOption
import org.revolution.blockly.BlocklyVariable
import org.revolution.blockly.view.result.BlockOptionResult
import org.revolution.blockly.view.result.ColorResult
import org.revolution.blockly.view.result.ConfirmResult
import org.revolution.blockly.view.result.DialpadResult
import org.revolution.blockly.view.result.DirectionResult
import org.revolution.blockly.view.result.DonutResult
import org.revolution.blockly.view.result.OptionResult
import org.revolution.blockly.view.result.SliderResult
import org.revolution.blockly.view.result.SoundResult
import org.revolution.blockly.view.result.TextResult
import org.revolution.blockly.view.result.VariableResult

@Suppress("ComplexInterface", "TooManyFunctions")
interface DialogFactory {

    fun showAlertDialog(message: String, result: ConfirmResult)
    fun showConfirmationDialog(message: String, result: ConfirmResult)
    fun showDirectionSelectorDialog(defaultValue: String, result: DirectionResult)
    fun showSlider(title: String, maxValue: Int, defaultValue: Int, result: SliderResult)
    fun showDialpad(defaultValue: Double, result: DialpadResult)
    fun showColorPicker(title: String, colors: List<String>, defaultValue: String, result: ColorResult)
    fun showSoundPicker(title: String, defaultValue: String?, result: SoundResult)
    fun showBlockOptionsDialog(title: String, comment: String, result: BlockOptionResult)
    fun showTextInput(title: String, defaultValue: String?, result: TextResult)
    fun showDonutSelector(defaultValue: String, isMultiSelection: Boolean, result: DonutResult)

    fun showOptionSelector(
        title: String,
        blocklyOptions: List<BlocklyOption>,
        defaultValue: BlocklyOption?,
        result: OptionResult
    )

    fun showVariableOptionsDialog(
        title: String,
        defaultValue: BlocklyVariable?,
        variables: List<BlocklyVariable>,
        result: VariableResult
    )
}
