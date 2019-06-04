package org.revolution.blockly.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import org.revolution.blockly.view.jsInterface.BlocklyJavascriptInterface
import org.revolution.blockly.view.jsInterface.BlocklyJavascriptListener
import org.revolution.blockly.view.jsInterface.IOJavascriptInterface

@Suppress("SetJavaScriptEnabled")
class BlocklyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    WebView(context, attrs, defStyleAttr) {

    private companion object {
        const val BRIDGE_NAME = "NativeBridge"
    }

    private var javascriptInterface: IOJavascriptInterface = IOJavascriptInterface(context)

    init {
        settings.javaScriptEnabled = true
    }

    @Suppress("JavascriptInterface")
    fun init(htmlPath: String, dialogFactory: DialogFactory) {
        webChromeClient = BlocklyWebChromeClient(dialogFactory)
        loadUrl(htmlPath)
        addJavascriptInterface(IOJavascriptInterface(context), BRIDGE_NAME)
    }

    @SuppressLint("JavascriptInterface")
    override fun addJavascriptInterface(javascriptInterface: Any?, name: String?) {
        this.javascriptInterface = javascriptInterface as IOJavascriptInterface
        super.addJavascriptInterface(javascriptInterface, name)
    }

    override fun onDetachedFromWindow() {
        (javascriptInterface as? BlocklyJavascriptInterface)?.release()
        super.onDetachedFromWindow()
    }

    fun loadProgram(xml: String) {
        loadUrl("javascript:loadXMLProgram(`$xml}`)")
    }

    fun saveProgram(listener: BlocklyJavascriptListener) {
        javascriptInterface.listener = listener
        loadUrl("javascript:saveProgram()")
    }
}
