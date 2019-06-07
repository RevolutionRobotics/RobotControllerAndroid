package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.defaultInput

class MultiDonutSelectorHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) = message.startsWith("block_set_multiple_led")

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        dialogFactory.showDonutSelector(request.defaultInput(), true, result)
    }
}
