package org.revolution.blockly.view.result

import android.webkit.JsPromptResult
import org.revolution.blockly.BlocklyOption

class OptionResult(result: JsPromptResult) : BlocklyResult(result) {

    fun confirm(option: BlocklyOption) =
        confirm(option.key)

    fun confirm(optionKey: String) =
        confirmResult(optionKey)
}
