package org.revolution.blockly.view

import android.content.Context
import android.webkit.JsPromptResult
import org.json.JSONObject

interface DialogFactory {

    fun showTextInputDialog(result: JsPromptResult, parameters: JSONObject?, context: Context)

    fun showSliderDialog(result: JsPromptResult, parameters: JSONObject?, context: Context)

    fun customAction(json: JSONObject) = Unit
}