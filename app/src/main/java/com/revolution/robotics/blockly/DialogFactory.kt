package com.revolution.robotics.blockly

import android.webkit.JsPromptResult
import android.webkit.JsResult
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.blockly.dialogs.blockOptions.BlockOptionsDialog
import com.revolution.robotics.blockly.dialogs.colorPicker.ColorPickerDialog
import com.revolution.robotics.blockly.dialogs.confirm.ConfirmDialog
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

    override fun showAlertDialog(message: String, result: JsResult) {
        javascriptResultHandler.register(result)
        ConfirmDialog.newInstance(message, false).show(fragmentManager)
    }

    override fun showConfirmationDialog(message: String, result: JsResult) {
        javascriptResultHandler.register(result)
        ConfirmDialog.newInstance(message, true).show(fragmentManager)
    }

    override fun showDirectionSelectorDialog(defaultValue: String, result: JsPromptResult) {
        javascriptResultHandler.register(result)
        DirectionSelectorDialog.newInstance(Direction.getByValue(defaultValue)).show(fragmentManager)
    }

    override fun showDialpad(defaultValue: Double, result: JsPromptResult) {
        javascriptResultHandler.register(result)
        DialpadDialog.newInstance(defaultValue).show(fragmentManager)
    }

    override fun showSlider(title: String, maxValue: Int, defaultValue: Int, result: JsPromptResult) {
        javascriptResultHandler.register(result)
        SliderDialog.newInstance(title, maxValue, defaultValue).show(fragmentManager)
    }

    override fun showOptionSelector(
        title: String,
        blocklyOptions: List<BlocklyOption>,
        defaultValue: BlocklyOption?,
        result: JsPromptResult
    ) {
        javascriptResultHandler.register(result)
        val options = blocklyOptions.map { blocklyOption -> blocklyOption.toOption(defaultValue, resourceResolver) }
        OptionSelectorDialog.newInstance(title, options).show(fragmentManager)
    }

    override fun showColorPicker(title: String, colors: List<String>, defaultValue: String, result: JsPromptResult) {
        javascriptResultHandler.register(result)
        ColorPickerDialog.newInstance(title, colors, defaultValue).show(fragmentManager)
    }

    override fun showSoundPicker(title: String, defaultValue: String?, result: JsPromptResult) {
        javascriptResultHandler.register(result)
        SoundPickerDialog.newInstance(title, defaultValue).show(fragmentManager)
    }

    override fun showBlockOptionsDialog(title: String, comment: String, result: JsPromptResult) {
        javascriptResultHandler.register(result)
        BlockOptionsDialog.newInstance(title, comment).show(fragmentManager)
    }

    override fun showVariableOptionsDialog(
        title: String,
        selectedKey: String,
        variables: List<BlocklyVariable>,
        result: JsPromptResult
    ) {
        javascriptResultHandler.register(result)
        VariableOptionsDialog.newInstance(title, selectedKey, variables).show(fragmentManager)
    }

    override fun showTextInput(title: String, defaultValue: String?, result: JsPromptResult) {
        javascriptResultHandler.register(result)
        TextInputDialog.newInstance(title, defaultValue).show(fragmentManager)
    }

    override fun showDonutSelector(defaultValue: String, isMultiSelection: Boolean, result: JsPromptResult) {
        javascriptResultHandler.register(result)
        val selectionType =
            if (isMultiSelection) {
                DonutSelectorDialog.DonutSelectionType.MULTI
            } else {
                DonutSelectorDialog.DonutSelectionType.SINGLE
            }
        DonutSelectorDialog.newInstance(selectionType, defaultValue).show(fragmentManager)
    }

    private fun BlocklyOption.toOption(defaultOption: BlocklyOption?, resourceResolver: ResourceResolver): Option {
        val resourceName = "ic_blockly_${Regex("[^A-Za-z]").replace(key, "_").toLowerCase()}"
        return Option(resourceResolver.drawableResourceByName(resourceName), value, key, key == defaultOption?.key)
    }
}
