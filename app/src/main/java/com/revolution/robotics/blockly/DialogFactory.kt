package com.revolution.robotics.blockly

import android.webkit.JsPromptResult
import androidx.fragment.app.FragmentManager
import com.revolution.robotics.blockly.utils.JavascriptResultHandler
import org.revolution.blockly.view.DialogFactory

// TODO remove this suppress
@Suppress("UnusedPrivateMember")
class DialogFactory(
    private val javascriptResultHandler: JavascriptResultHandler,
    private val fragmentManager: FragmentManager?
) : DialogFactory {

    override fun showTextInputDialog(result: JsPromptResult, options: DialogFactory.TextOptions) {
        javascriptResultHandler.registerResult(result)
        // TODO show text input dialog here
    }

    override fun showSliderDialog(result: JsPromptResult, options: DialogFactory.SliderOptions) {
        javascriptResultHandler.registerResult(result)
        // TODO show slider dialog here
    }
}
