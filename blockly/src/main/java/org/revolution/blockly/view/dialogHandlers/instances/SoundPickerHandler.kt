package org.revolution.blockly.view.dialogHandlers.instances

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.view.DialogFactory
import org.revolution.blockly.view.dialogHandlers.JsPromptHandler
import org.revolution.blockly.view.dialogHandlers.defaultKey
import org.revolution.blockly.view.dialogHandlers.title

class SoundPickerHandler : JsPromptHandler {

    override fun canHandleRequest(message: String) = message == "play_tune.in_sound"

    override fun handleRequest(request: JSONObject, dialogFactory: DialogFactory, result: JsPromptResult) {
        dialogFactory.showSoundPicker(request.title(), request.defaultKey(), result)
    }
}
