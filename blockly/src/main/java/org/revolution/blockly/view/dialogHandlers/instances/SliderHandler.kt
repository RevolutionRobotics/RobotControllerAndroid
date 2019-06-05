package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.defaultInput
import org.revolution.blockly.view.dialogHandlers.maxValue
import org.revolution.blockly.view.dialogHandlers.title

class SliderHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) = message == "math_number.num"

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        dialogFactory.showSlider(request.title(), request.maxValue(), request.defaultInput(), result)
    }
}
