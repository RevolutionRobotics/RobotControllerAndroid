package org.revolution.blockly.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import org.revolution.blockly.view.jsInterface.BlocklyJavascriptInterface

@Suppress("SetJavaScriptEnabled")
class BlocklyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    WebView(context, attrs, defStyleAttr) {

    private var javascriptInterface: BlocklyJavascriptInterface? = null

    init {
        webChromeClient = BlocklyWebChromeClient()
        settings.javaScriptEnabled = true
    }

    @Suppress("JavascriptInterface")
    fun init(htmlPath: String, interfaceDescriptor: JavascriptInterfaceDescriptor? = null) {
        if (interfaceDescriptor != null) {
            this.javascriptInterface = interfaceDescriptor.javascriptInterface
            addJavascriptInterface(javascriptInterface, interfaceDescriptor.name)
        }

        loadUrl(htmlPath)
    }

    override fun onDetachedFromWindow() {
        javascriptInterface?.release()
        super.onDetachedFromWindow()
    }

    data class JavascriptInterfaceDescriptor(val javascriptInterface: BlocklyJavascriptInterface, val name: String)
}
