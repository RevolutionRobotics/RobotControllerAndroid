package org.revolution.blockly.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import org.revolution.blockly.view.jsInterface.BlocklyJavascriptInterface

@Suppress("SetJavaScriptEnabled")
class BlocklyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    WebView(context, attrs, defStyleAttr) {

    private var javascriptInterface: Any? = null

    init {
        settings.javaScriptEnabled = true
    }

    @Suppress("JavascriptInterface")
    fun init(htmlPath: String, dialogFactory: DialogFactory) {
        webChromeClient = BlocklyWebChromeClient(dialogFactory)
        loadUrl(htmlPath)
    }

    @SuppressLint("JavascriptInterface")
    override fun addJavascriptInterface(javascriptInterface: Any?, name: String?) {
        this.javascriptInterface = javascriptInterface
        super.addJavascriptInterface(javascriptInterface, name)
    }

    override fun onDetachedFromWindow() {
        (javascriptInterface as? BlocklyJavascriptInterface)?.release()
        super.onDetachedFromWindow()
    }
}
