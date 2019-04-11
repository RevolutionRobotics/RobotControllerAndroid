package com.revolution.robotics.blockly.utils

import android.webkit.JsPromptResult

class JavascriptResultHandler {

    private var javascriptResult: JsPromptResult? = null

    fun registerResult(result: JsPromptResult) {
        javascriptResult?.cancel()
        javascriptResult = result
    }

    fun confirmResult(result: String) {
        javascriptResult?.confirm(result)
        javascriptResult = null
    }

    fun cancelResult() {
        javascriptResult?.cancel()
        javascriptResult = null
    }
}
