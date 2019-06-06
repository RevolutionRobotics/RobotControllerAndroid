package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.defaultInput

class DialpadHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) = message.endsWith("math_number.num")

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        dialogFactory.showDialpad(request.defaultInput("0").toDouble(), result)
    }
}
