package org.revolution.blockly.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

@Suppress("SetJavaScriptEnabled")
class BlocklyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    WebView(context, attrs, defStyleAttr) {

    companion object {
        private const val ASSET_PATH = "file:///android_asset/blockly/webview.html"
    }

    init {
        webChromeClient = BlocklyWebChromeClient()
        settings.javaScriptEnabled = true
        loadUrl(ASSET_PATH)
    }
}