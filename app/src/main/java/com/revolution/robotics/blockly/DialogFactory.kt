package com.revolution.robotics.blockly

import android.webkit.JsPromptResult
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.blockly.dialogs.colorPicker.ColorPickerDialog
import com.revolution.robotics.blockly.dialogs.directionSelector.Direction
import com.revolution.robotics.blockly.dialogs.directionSelector.DirectionSelectorDialog
import com.revolution.robotics.blockly.dialogs.optionSelector.Option
import com.revolution.robotics.blockly.dialogs.optionSelector.OptionSelectorDialog
import com.revolution.robotics.blockly.dialogs.slider.SliderDialog
import com.revolution.robotics.blockly.dialogs.soundPicker.SoundPickerDialog
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import org.revolution.blockly.BlocklyOption
import org.revolution.blockly.view.DialogFactory

class DialogFactory(
    private val javascriptResultHandler: JavascriptResultHandler,
    private val resourceResolver: ResourceResolver,
    private val fragmentManager: FragmentManager?
) : DialogFactory {

    override fun showDirectionSelectorDialog(defaultValue: String, result: JsPromptResult) {
        javascriptResultHandler.registerResult(result)
        DirectionSelectorDialog.newInstance(Direction.getByValue(defaultValue)).show(fragmentManager)
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

    override fun showSoundPicker(title: String, result: JsPromptResult) {
        javascriptResultHandler.registerResult(result)
        SoundPickerDialog.newInstance(title).show(fragmentManager)
    }

    private fun BlocklyOption.toOption(defaultOption: BlocklyOption?, resourceResolver: ResourceResolver): Option {
        val resourceName = "ic_blockly_${Regex("[^A-Za-z]").replace(key, "_").toLowerCase()}"
        return Option(resourceResolver.drawableResourceByName(resourceName), value, key, key == defaultOption?.key)
    }
}
