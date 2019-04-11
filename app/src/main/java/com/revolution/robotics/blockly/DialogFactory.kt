package com.revolution.robotics.blockly

import android.content.Context
import android.view.LayoutInflater
import android.webkit.JsPromptResult
import com.revolution.robotics.blockly.dialogs.SliderDialogViewModel
import com.revolution.robotics.blockly.dialogs.TextInputDialogViewModel
import com.revolution.robotics.databinding.BlocklyDialogInputTextBinding
import com.revolution.robotics.databinding.BlocklyDialogSliderBinding
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory

class DialogFactory : DialogFactory {

    override fun showTextInputDialog(result: JsPromptResult, options: DialogFactory.TextOptions, context: Context) {
        BlocklyDialogInputTextBinding.inflate(LayoutInflater.from(context), null, false).apply {
            viewModel = TextInputDialogViewModel(result, this)
            viewModel?.createAndShowDialog()
        }
    }

    override fun showSliderDialog(result: JsPromptResult, options: DialogFactory.SliderOptions, context: Context) {
        BlocklyDialogSliderBinding.inflate(LayoutInflater.from(context), null, false).apply {
            viewModel = SliderDialogViewModel(result, this).apply {
                minValue = options.minValue
                maxValue = options.maxValue
            }
            viewModel?.createAndShowDialog()
        }
    }
}
