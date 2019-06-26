package org.revolution.blockly.view.result

import android.webkit.JsPromptResult

class ColorResult(result: JsPromptResult) : BlocklyResult(result) {

    fun confirm(color: String) =
        confirmResult(color)
}
