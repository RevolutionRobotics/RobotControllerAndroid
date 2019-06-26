package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.comment
import org.revolution.blockly.view.dialogHandlers.title
import org.revolution.blockly.view.result.BlockOptionResult

class BlockOptionsHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) = message == "block_context"

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        dialogFactory.showBlockOptionsDialog(request.title(), request.comment(), BlockOptionResult(result))
    }
}
