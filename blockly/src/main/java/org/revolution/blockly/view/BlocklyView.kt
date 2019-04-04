package org.revolution.blockly.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast

@Suppress("SetJavaScriptEnabled")
class BlocklyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    WebView(context, attrs, defStyleAttr) {

    companion object {
        // TODO remove POC implementation
        // private const val ASSET_PATH = "file:///android_asset/blockly/webview.html"
        private const val ASSET_PATH = "file:///android_asset/poc.html"
    }

    init {
        webChromeClient = BlocklyWebChromeClient()
        settings.javaScriptEnabled = true
        loadUrl(ASSET_PATH)

        addJavascriptInterface(JsInterface(), "NativePoc")
    }

    inner class JsInterface {

        @JavascriptInterface
        fun createMessage(input: String) =
            "$input World"

        @JavascriptInterface
        fun displayMessage(input: String) =
            Toast.makeText(context, input, Toast.LENGTH_LONG).show()
    }
}
