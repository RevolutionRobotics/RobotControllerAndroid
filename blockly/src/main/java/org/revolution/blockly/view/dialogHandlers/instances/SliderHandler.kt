package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.defaultValue
import org.revolution.blockly.view.dialogHandlers.maxValue
import org.revolution.blockly.view.dialogHandlers.title

class SliderHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) = message.contains("_slider")

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        dialogFactory.showSlider(request.title(), request.maxValue(), request.defaultValue("0").toInt(), result)
    }
}
