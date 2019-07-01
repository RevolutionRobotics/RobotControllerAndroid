package org.revolution.blockly.view.result

import android.webkit.JsPromptResult
import org.json.JSONObject

class BlockOptionResult(result: JsPromptResult) : BlocklyResult(result) {

    companion object {
        private const val ACTION_DELETE = "DELETE_BLOCK"
        private const val ACTION_DUPLICATE = "DUPLICATE_BLOCK"
        private const val ACTION_COMMENT = "ADD_COMMENT"
    }

    fun confirmDelete() =
        confirm(ACTION_DELETE)

    fun confirmDuplicate() =
        confirm(ACTION_DUPLICATE)

    fun confirmComment(comment: String?) =
        confirm(ACTION_COMMENT, comment)

    private fun confirm(action: String, payload: String? = "") =
        confirmResult(
            JSONObject().apply {
                put("type", action)
                put("payload", payload)
            }.toString()
        )
}
