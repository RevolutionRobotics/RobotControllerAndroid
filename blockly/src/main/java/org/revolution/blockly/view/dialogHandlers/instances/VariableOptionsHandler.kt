package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.defaultKey
import org.revolution.blockly.view.dialogHandlers.title
import org.revolution.blockly.view.dialogHandlers.variables

class VariableOptionsHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) = message == "variables_get.var"

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        dialogFactory.showVariableOptionsDialog(request.title(), request.defaultKey(), request.variables(), result)
    }
}
