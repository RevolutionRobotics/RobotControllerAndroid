package org.revolution.blockly.view

import android.content.Context
import android.webkit.JsPromptResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import org.json.JSONObject

class BlocklyWebChromeClient(dialogFactory: DialogFactory) : WebChromeClient() {

    private val factoryMethods = hashMapOf<String, ((JsPromptResult, JSONObject, Context) -> Unit)>(
        "text" to dialogFactory::showTextInputDialog,
        "slider" to dialogFactory::showSliderDialog
    )
    private val defaultMethod = dialogFactory::customAction

    override fun onJsPrompt(
        view: WebView?,
        url: String?,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult?
    ) =
        if (view != null && message != null && result != null) {
            // we didn't want to add the GSON dependency to a library module, so we're left with JSONObject
            val json = JSONObject(message)
            val method = factoryMethods[json.getString("type")]
            if (method == null) {
                defaultMethod.invoke(json)
            } else {
                method.invoke(result, json.getJSONObject("options"), view.context)
            }
            true
        } else {
            super.onJsPrompt(view, url, message, defaultValue, result)
        }
}
