package org.revolution.blockly.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import org.revolution.blockly.view.jsInterface.BlocklyJavascriptListener
import org.revolution.blockly.view.jsInterface.IOJavascriptInterface

@Suppress("SetJavaScriptEnabled")
class BlocklyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    WebView(context, attrs, defStyleAttr), BlocklyLoadedListener {

    var listener: BlocklyLoadedListener? = null
        set(value) {
            field = value
            if (isBlocklyLoaded) {
                listener?.onBlocklyLoaded()
            }
        }

    private companion object {
        const val BRIDGE_NAME = "NativeBridge"
        const val USER_AGENT = "Android-Blockly"
    }

    private var javascriptInterface = IOJavascriptInterface(context)
    private var isBlocklyLoaded = false

    init {
        settings.javaScriptEnabled = true
        settings.userAgentString = USER_AGENT
    }

    @Suppress("JavascriptInterface")
    fun init(htmlPath: String, dialogFactory: DialogFactory) {
        webChromeClient = BlocklyWebChromeClient(dialogFactory, this)
        loadUrl(htmlPath)
        addJavascriptInterface(javascriptInterface, BRIDGE_NAME)
    }

    override fun onDetachedFromWindow() {
        javascriptInterface.release()
        super.onDetachedFromWindow()
    }

    fun loadProgram(xml: String) {
        loadUrl("javascript:loadXMLProgram(`$xml}`)")
    }

    fun saveProgram(listener: BlocklyJavascriptListener) {
        javascriptInterface.listener = listener
        loadUrl("javascript:saveProgram()")
    }

    fun clearWorkspace() {
        loadUrl("javascript:clearWorkspace()")
    }

    override fun onBlocklyLoaded() {
        isBlocklyLoaded = true
        listener?.onBlocklyLoaded()
    }
}
