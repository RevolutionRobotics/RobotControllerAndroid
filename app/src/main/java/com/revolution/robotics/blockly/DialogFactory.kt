package com.revolution.robotics.blockly

import android.webkit.JsPromptResult
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.blockly.dialogs.slider.SliderDialog
import com.revolution.robotics.blockly.dialogs.text.TextInputDialog
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import org.revolution.blockly.view.DialogFactory

class DialogFactory(
    private val javascriptResultHandler: JavascriptResultHandler,
    private val fragmentManager: FragmentManager?
) : DialogFactory {

    override fun showTextInputDialog(result: JsPromptResult, options: DialogFactory.TextOptions) {
        javascriptResultHandler.registerResult(result)
        TextInputDialog.newInstance(options).show(fragmentManager, TextInputDialog::class.java.simpleName)
    }

    override fun showSliderDialog(result: JsPromptResult, options: DialogFactory.SliderOptions) {
        javascriptResultHandler.registerResult(result)
        val dialog = SliderDialog.newInstance(options)
        dialog.show(fragmentManager, SliderDialog::class.java.simpleName)
    }
}
