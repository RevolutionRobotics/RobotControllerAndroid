package com.revolution.robotics.blockly

import android.webkit.JsPromptResult
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.blockly.dialogs.blockOptions.BlockOptionsDialog
import com.revolution.robotics.blockly.dialogs.colorPicker.ColorPickerDialog
import com.revolution.robotics.blockly.dialogs.dialpad.DialpadDialog
import com.revolution.robotics.blockly.dialogs.directionSelector.Direction
import com.revolution.robotics.blockly.dialogs.directionSelector.DirectionSelectorDialog
import com.revolution.robotics.blockly.dialogs.donutSelector.DonutSelectorDialog
import com.revolution.robotics.blockly.dialogs.optionSelector.Option
import com.revolution.robotics.blockly.dialogs.optionSelector.OptionSelectorDialog
import com.revolution.robotics.blockly.dialogs.slider.SliderDialog
import com.revolution.robotics.blockly.dialogs.soundPicker.SoundPickerDialog
import com.revolution.robotics.blockly.dialogs.textInput.TextInputDialog
import com.revolution.robotics.blockly.dialogs.variableOptions.VariableOptionsDialog
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import org.revolution.blockly.BlocklyOption
import org.revolution.blockly.BlocklyVariable
import org.revolution.blockly.view.DialogFactory

@Suppress("TooManyFunctions")
class DialogFactory(
    private val javascriptResultHandler: JavascriptResultHandler,
    private val resourceResolver: ResourceResolver,
    private val fragmentManager: FragmentManager?
) : DialogFactory {

    override fun showDirectionSelectorDialog(defaultValue: String, result: JsPromptResult) {
        javascriptResultHandler.registerResult(result)
        DirectionSelectorDialog.newInstance(Direction.getByValue(defaultValue)).show(fragmentManager)
    }

    override fun showDialpad(defaultValue: Double, result: JsPromptResult) {
        javascriptResultHandler.registerResult(result)
        DialpadDialog.newInstance(defaultValue).show(fragmentManager)
    }

    override fun showSlider(title: String, maxValue: Int, defaultValue: Int, result: JsPromptResult) {
        javascriptResultHandler.registerResult(result)
        SliderDialog.newInstance(title, maxValue, defaultValue).show(fragmentManager)
    }

    override fun showOptionSelector(
        title: String,
        blocklyOptions: List<BlocklyOption>,
        default: BlocklyOption?,
        result: JsPromptResult
    ) {
        javascriptResultHandler.registerResult(result)
        val options = blocklyOptions.map { blocklyOption -> blocklyOption.toOption(default, resourceResolver) }
        OptionSelectorDialog.newInstance(title, options).show(fragmentManager)
    }

    override fun showColorPicker(title: String, colors: List<String>, selectedColor: String, result: JsPromptResult) {
        javascriptResultHandler.registerResult(result)
        ColorPickerDialog.newInstance(title, colors, selectedColor).show(fragmentManager)
    }

    override fun showSoundPicker(title: String, selectedSound: String?, result: JsPromptResult) {
        javascriptResultHandler.registerResult(result)
        SoundPickerDialog.newInstance(title, selectedSound).show(fragmentManager)
    }

    override fun showBlockOptionsDialog(title: String, comment: String, result: JsPromptResult) {
        javascriptResultHandler.registerResult(result)
        BlockOptionsDialog.newInstance(title, comment).show(fragmentManager)
    }

    override fun showVariableOptionsDialog(
        title: String,
        selectedKey: String,
        variables: List<BlocklyVariable>,
        result: JsPromptResult
    ) {
        javascriptResultHandler.registerResult(result)
        VariableOptionsDialog.newInstance(title, selectedKey, variables).show(fragmentManager)
    }

    override fun showTextInput(title: String, defaultValue: String?, result: JsPromptResult) {
        javascriptResultHandler.registerResult(result)
        TextInputDialog.newInstance(title, defaultValue).show(fragmentManager)
    }

    override fun showDonutSelector(defaultSelection: String, isMultiSelection: Boolean, result: JsPromptResult) {
        javascriptResultHandler.registerResult(result)
        val selectionType =
            if (isMultiSelection) {
                DonutSelectorDialog.DonutSelectionType.MULTI
            } else {
                DonutSelectorDialog.DonutSelectionType.SINGLE
            }
        DonutSelectorDialog.newInstance(selectionType, defaultSelection).show(fragmentManager)
    }

    private fun BlocklyOption.toOption(defaultOption: BlocklyOption?, resourceResolver: ResourceResolver): Option {
        val resourceName = "ic_blockly_${Regex("[^A-Za-z]").replace(key, "_").toLowerCase()}"
        return Option(resourceResolver.drawableResourceByName(resourceName), value, key, key == defaultOption?.key)
    }
}
