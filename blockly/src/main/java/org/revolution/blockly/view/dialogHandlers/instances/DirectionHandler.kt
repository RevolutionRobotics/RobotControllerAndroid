package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.defaultKey

class DirectionHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) = message == "block_drive.direction_selector"

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        dialogFactory.showDirectionSelectorDialog(request.defaultKey(), result)
    }
}
