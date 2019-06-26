package org.revolution.blockly.view.result

import android.webkit.JsPromptResult
import org.json.JSONObject
import org.revolution.blockly.BlocklyVariable

class VariableResult(result: JsPromptResult) : BlocklyResult(result) {

    companion object {
        private const val ACTION_CHANGE_VARIABLE = "SET_VARIABLE_ID"
        private const val ACTION_DELETE_VARIABLE = "DELETE_VARIABLE_ID"
    }

    fun confirmChangeVariable(variable: BlocklyVariable) =
        confirm(ACTION_CHANGE_VARIABLE, variable.key)

    fun confirmDeleteVariable(variable: BlocklyVariable) =
        confirm(ACTION_DELETE_VARIABLE, variable.key)

    private fun confirm(action: String, variableKey: String) =
        confirmResult(
            JSONObject().apply {
                put("type", action)
                put("payload", variableKey)
            }.toString()
        )
}
