package org.revolution.blockly.view

import android.webkit.JsPromptResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import org.json.JSONObject
import org.revolution.blockly.BlocklyOption

class BlocklyWebChromeClient(private val dialogFactory: DialogFactory) : WebChromeClient() {

    // TODO add dialpad
    // TODO add text input
    // TODO add donut selector
    @Suppress("SwallowedException", "ComplexMethod")
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

            if (message == "block_drive.direction_selector") {
                dialogFactory.showDirectionSelectorDialog(json.defaultKey(), result)
            } else if (message == "math_number.num") {
                dialogFactory.showSlider(json.title(), json.maxValue(), json.defaultInput(), result)
            } else if (message.endsWith("_selector") ||
                message == "logic_boolean.bool" ||
                message == "logic_compare.op"
            ) {
                dialogFactory.showOptionSelector(json.title(), json.getOptions(), json.getDefaultOption(), result)
            } else if (message == "colour_picker.colour") {
                dialogFactory.showColorPicker(json.title(), json.getColors(), json.defaultKey(), result)
            } else if (message == "play_tune.in_sound") {
                dialogFactory.showSoundPicker(json.title(), result)
            } else if (message == "block_context") {
                dialogFactory.showBlockOptionsDialog(json.title(), json.comment(), result)
            } else {
                // TODO remove super call
                wasDialogHandled = super.onJsPrompt(view, url, message, defaultValue, result)
            }
            wasDialogHandled
        } else {
            super.onJsPrompt(view, url, message, defaultValue, result)
        }

    // JSON extensions

    private fun JSONObject.defaultKey() =
        optString("defaultKey")

    private fun JSONObject.getDefaultOption() =
        getOptions().find { it.key == defaultKey() }

    private fun JSONObject.defaultInput() =
        Integer.parseInt(optString("defaultInput", "0"))

    private fun JSONObject.maxValue() =
        Integer.parseInt(optString("maxValue", "100"))

    private fun JSONObject.title() =
        optString("title", "")

    private fun JSONObject.comment() =
        optString("comment")

    private fun JSONObject.getOptions(): List<BlocklyOption> =
        ArrayList<BlocklyOption>().apply {
            withOptions { option ->
                add(BlocklyOption(option.getString("key"), option.getString("value")))
            }
        }

    private fun JSONObject.getColors(): List<String> =
        ArrayList<String>().apply {
            withOptions { option ->
                add(option.getString("key"))
            }
        }

    private fun JSONObject.withOptions(callback: (option: JSONObject) -> Unit) {
        val options = getJSONArray("options")
        repeat(options.length()) { index ->
            val option = options.getJSONObject(index)
            callback.invoke(option)
        }
    }
}