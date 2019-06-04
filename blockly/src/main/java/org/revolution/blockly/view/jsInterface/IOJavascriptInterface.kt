package org.revolution.blockly.view.jsInterface

import android.content.Context
import android.webkit.JavascriptInterface

class IOJavascriptInterface(ctx: Context) : BlocklyJavascriptInterface {

    private var context: Context? = ctx
    var listener: BlocklyJavascriptListener? = null

    @JavascriptInterface
    fun onPythonProgramSaved(file: String) {
        listener?.onPythonProgramSaved(file)
    }

    @JavascriptInterface
    fun onXMLProgramSaved(file: String) {
        listener?.onXMLProgramSaved(file)
    }

    @JavascriptInterface
    fun onVariablesExported(variables: String) {
        listener?.onVariablesExported(variables)
    }

    override fun release() {
        context = null
        listener = null
    }
}
