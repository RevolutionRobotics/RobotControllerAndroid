package org.revolution.blockly.view.result

import android.webkit.JsPromptResult

class DirectionResult(result: JsPromptResult) : BlocklyResult(result) {

    companion object {
        private const val RESULT_FORWARD = "Motor.DIRECTION_FWD"
        private const val RESULT_BACKWARD = "Motor.DIRECTION_BACK"
        private const val RESULT_TURN_LEFT = "Motor.DIRECTION_LEFT"
        private const val RESULT_TURN_RIGHT = "Motor.DIRECTION_RIGHT"
    }

    fun confirmForward() =
        confirmResult(RESULT_FORWARD)

    fun confirmBackward() =
        confirmResult(RESULT_BACKWARD)

    fun confirmTurnLeft() =
        confirmResult(RESULT_TURN_LEFT)

    fun confirmTurnRight() =
        confirmResult(RESULT_TURN_RIGHT)
}
