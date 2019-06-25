package com.revolution.robotics.blockly.utils

import android.webkit.JsPromptResult
import android.webkit.JsResult

class JavascriptResultHandler {

    private var result: JsResult? = null
    private var promptResult: JsPromptResult? = null

    fun register(result: JsResult) {
        this.result?.cancel()
        this.result = result
    }

    fun confirmResult() {
        result?.confirm()
        result = null
    }

    fun cancelResult() {
        result?.cancel()
        result = null
    }

    fun register(result: JsPromptResult) {
        promptResult?.cancel()
        promptResult = result
    }

    fun confirmPromptResult(result: String) {
        promptResult?.confirm(result)
        promptResult = null
    }

    fun cancelPromptResult() {
        promptResult?.cancel()
        promptResult = null
    }
}
