package org.revolution.blockly.view.result

import android.webkit.JsPromptResult

class DialpadResult(result: JsPromptResult) : BlocklyResult(result) {

    fun confirm(number: Double) =
        confirmResult("$number")
}
