package org.revolution.blockly.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import org.revolution.blockly.view.jsInterface.IOJavascriptInterface
import org.revolution.blockly.view.jsInterface.SaveBlocklyListener

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

        private const val HTML_PATH = "file:///android_asset/blockly/webview.html"
    }

    private var javascriptInterface = IOJavascriptInterface(context)
    private var isBlocklyLoaded = false

    init {
        settings.javaScriptEnabled = true
        settings.userAgentString = USER_AGENT
    }

    @Suppress("JavascriptInterface")
    fun init(dialogFactory: DialogFactory) {
        webChromeClient = BlocklyWebChromeClient(dialogFactory, this)
        loadUrl(HTML_PATH)
        addJavascriptInterface(javascriptInterface, BRIDGE_NAME)
    }

    override fun onDetachedFromWindow() {
        javascriptInterface.release()
        super.onDetachedFromWindow()
    }

    fun clearWorkspace() {
        loadUrl("javascript:clearWorkspace()")
    }

    fun loadProgram(xml: String) {
        loadUrl("javascript:loadXMLProgram(`$xml}`)")
    }

    fun saveProgram(listener: SaveBlocklyListener) {
        javascriptInterface.listener = listener
        loadUrl("javascript:saveProgram()")
    }

    override fun onBlocklyLoaded() {
        isBlocklyLoaded = true
        listener?.onBlocklyLoaded()
    }
}
