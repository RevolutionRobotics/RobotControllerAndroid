package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.colors
import org.revolution.blockly.view.dialogHandlers.defaultKey
import org.revolution.blockly.view.dialogHandlers.title
import org.revolution.blockly.view.result.ColorResult

class ColorPickerHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) = message == "colour_picker.colour"

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        dialogFactory.showColorPicker(request.title(), request.colors(), request.defaultKey(), ColorResult(result))
    }
}
