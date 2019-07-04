package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.defaultInput
import org.revolution.blockly.view.dialogHandlers.title
import org.revolution.blockly.view.result.TextResult

class TextInputHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) =
        message.endsWith(".name_input") ||
                message == "variable" ||
                message == "procedures_mutatorarg.name" ||
                message == "procedures_defreturn.name"

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        dialogFactory.showTextInput(request.title(), request.defaultInput(), TextResult(result))
    }
}
