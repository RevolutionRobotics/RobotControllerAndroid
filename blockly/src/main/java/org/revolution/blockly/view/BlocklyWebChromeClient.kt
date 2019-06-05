package org.revolution.blockly.view

import android.webkit.JsPromptResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import org.json.JSONObject
import org.revolution.blockly.view.dialogHandlers.instances.BlockOptionsHandler
import org.revolution.blockly.view.dialogHandlers.instances.ColorPickerHandler
import org.revolution.blockly.view.dialogHandlers.instances.DirectionHandler
import org.revolution.blockly.view.dialogHandlers.instances.OptionSelectorHandler
import org.revolution.blockly.view.dialogHandlers.instances.SliderHandler
import org.revolution.blockly.view.dialogHandlers.instances.SoundPickerHandler
import org.revolution.blockly.view.dialogHandlers.instances.TextInputHandler

class BlocklyWebChromeClient(
    private val dialogFactory: DialogFactory,
    private val listener: BlocklyLoadedListener
) : WebChromeClient() {

    companion object {
        private const val PROGRESS_COMPLETE = 100
    }

    private val promptHandlers = listOf(
        TextInputHandler(),
        OptionSelectorHandler(),
        DirectionHandler(),
        SliderHandler(),
        SoundPickerHandler(),
        ColorPickerHandler(),
        BlockOptionsHandler()
    )

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (newProgress == PROGRESS_COMPLETE) {
            listener.onBlocklyLoaded()
        }
    }

    // TODO add dialpad
    // TODO add donut selector
    override fun onJsPrompt(
        view: WebView,
        url: String,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult
    ) =
        if (message != null && message.isNotEmpty()) {
            var wasDialogHandled = true
            val json = JSONObject(defaultValue)
            promptHandlers.find { it.canHandleRequest(message) }?.let { handler ->
                wasDialogHandled = true
                handler.handleRequest(JSONObject(defaultValue), dialogFactory, result)
            }
            wasDialogHandled
        } else {
            super.onJsPrompt(view, url, message, defaultValue, result)
        }
}
