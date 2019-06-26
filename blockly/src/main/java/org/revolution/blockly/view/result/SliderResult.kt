package org.revolution.blockly.view.result

import android.webkit.JsPromptResult

class SliderResult(result: JsPromptResult) : BlocklyResult(result) {

    fun confirm(number: Int) =
        confirmResult("$number")
}
