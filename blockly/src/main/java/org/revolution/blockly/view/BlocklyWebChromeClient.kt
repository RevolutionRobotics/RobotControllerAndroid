package org.revolution.blockly.view

import android.webkit.JsPromptResult
import android.webkit.WebChromeClient
import android.webkit.WebView

class BlocklyWebChromeClient(private val dialogFactory: DialogFactory) : WebChromeClient() {

    @Suppress("SwallowedException")
    override fun onJsPrompt(
        view: WebView,
        url: String,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult
    ) =
        if (message != null && message.isNotEmpty()) {
            var wasDialogHandled = true
            when (message) {
                "Dropdown selector" -> dialogFactory.showDirectionSelectorDialog(result)
                else -> wasDialogHandled = super.onJsPrompt(view, url, message, defaultValue, result)
            }
            wasDialogHandled
        } else {
            super.onJsPrompt(view, url, message, defaultValue, result)
        }
}
