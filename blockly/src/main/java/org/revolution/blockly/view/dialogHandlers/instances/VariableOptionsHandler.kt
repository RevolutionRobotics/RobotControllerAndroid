package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.defaultKey
import org.revolution.blockly.view.dialogHandlers.title
import org.revolution.blockly.view.dialogHandlers.variables
import org.revolution.blockly.view.result.VariableResult

class VariableOptionsHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) = message.endsWith(".var")

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        val variables = request.variables()
        dialogFactory.showVariableOptionsDialog(
            request.title(),
            variables.firstOrNull { it.key == request.defaultKey() },
            variables,
            VariableResult(result)
        )
    }
}
