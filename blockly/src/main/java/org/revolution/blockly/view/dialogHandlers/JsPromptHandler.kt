package org.revolution.blockly.view.dialogHandlers

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory

interface JsPromptHandler {
    fun canHandleRequest(message: String): Boolean
    fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult)
}
