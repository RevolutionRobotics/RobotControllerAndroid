package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.defaultOption
import org.revolution.blockly.view.dialogHandlers.options
import org.revolution.blockly.view.dialogHandlers.title
import org.revolution.blockly.view.result.OptionResult

class OptionSelectorHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) =
        message.endsWith("_selector") ||
                message.endsWith("logic_boolean.bool") ||
                message.endsWith("logic_compare.op")

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        dialogFactory.showOptionSelector(
            request.title(),
            request.options(),
            request.defaultOption(),
            OptionResult(result)
        )
    }
}
