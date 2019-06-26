package org.revolution.blockly.view.result

import android.webkit.JsPromptResult
import android.webkit.JsResult

@Suppress("UnnecessaryAbstractClass")
abstract class BlocklyResult(result: JsResult?, promptResult: JsPromptResult?) {

    constructor(result: JsResult) : this(result, null)

    constructor(promptResult: JsPromptResult) : this(null, promptResult)

    private val jsResult = result
    private val jsPromptResult = promptResult

    fun cancel() {
        jsResult?.cancel()
        jsPromptResult?.cancel()
    }

    protected fun confirmResult(result: String? = null) {
        jsResult?.confirm()
        jsPromptResult?.confirm(result)
    }
}
