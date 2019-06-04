package org.revolution.blockly.view.jsInterface

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface

class IOJavascriptInterface(ctx: Context) : BlocklyJavascriptInterface {

    private var context: Context? = ctx

    @JavascriptInterface
    fun onPythonProgramSaved(file: String) {
        Log.e("TEST", "Python$file")
        // TODO (Save Python)
    }

    @JavascriptInterface
    fun onXMLProgramSaved(file: String) {
        Log.e("TEST", "XML$file")
        // TODO (Save XML)
    }

    @JavascriptInterface
    fun onVariablesExported(variables: String) {
        Log.e("TEST", "Variables$variables")
        // TODO (Save XML)
    }

    override fun release() {
        context = null
    }
}
