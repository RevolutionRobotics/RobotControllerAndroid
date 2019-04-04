package org.revolution.blockly.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import org.revolution.blockly.view.jsInterface.AudioHandlerJavascriptInterface

@Suppress("SetJavaScriptEnabled")
class BlocklyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    WebView(context, attrs, defStyleAttr) {

    private val jsInterface = AudioHandlerJavascriptInterface(context)

    companion object {
        // TODO remove POC implementation
        // private const val ASSET_PATH = "file:///android_asset/blockly/webview.html"
        private const val ASSET_PATH = "file:///android_asset/play_sound.html"
    }

    init {
        webChromeClient = BlocklyWebChromeClient()
        settings.javaScriptEnabled = true
        addJavascriptInterface(jsInterface, "Native")
        loadUrl(ASSET_PATH)
    }

    override fun onDetachedFromWindow() {
        jsInterface.release()
        super.onDetachedFromWindow()
    }

}
