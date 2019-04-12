package org.revolution.blockly.view

import android.webkit.JsPromptResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import org.json.JSONObject

class BlocklyWebChromeClient(private val dialogFactory: DialogFactory) : WebChromeClient() {

    companion object {
        const val DEFAULT_TEXT_TITLE = ""
        const val DEFAULT_SLIDER_MIN_VALUE = 0
        const val DEFAULT_SLIDER_MAX_VALUE = 100
    }

    override fun onJsPrompt(
        view: WebView,
        url: String,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult
    ) =
        if (message != null) {
            // we didn't want to add the GSON dependency to a library module, so we're left with JSONObject
            val json = JSONObject(message)
            val options: JSONObject? = json.optJSONObject("options")
            var wasDialogCreated = true
            when (json["type"]) {
                "text" -> dialogFactory.showTextInputDialog(result, options.toTextOptions())
                "slider" -> dialogFactory.showSliderDialog(result, options.toSliderOptions())
                else -> {
                    super.onJsPrompt(view, url, message, defaultValue, result)
                    wasDialogCreated = false
                }
            }
            wasDialogCreated
        } else {
            super.onJsPrompt(view, url, message, defaultValue, result)
        }

    // JSON converters --------------------------------------------------------
    private fun JSONObject?.toTextOptions() = DialogFactory.TextOptions(
        string("title", DEFAULT_TEXT_TITLE)
    )

    private fun JSONObject?.toSliderOptions() = DialogFactory.SliderOptions(
        int("minValue", DEFAULT_SLIDER_MIN_VALUE),
        int("maxValue", DEFAULT_SLIDER_MAX_VALUE)
    )

    // JSON type accessors ----------------------------------------------------
    private fun JSONObject?.string(key: String, defaultValue: String) =
        this?.optString(key, defaultValue) ?: defaultValue

    private fun JSONObject?.int(key: String, defaultValue: Int) =
        this?.optInt(key, defaultValue) ?: defaultValue
}
