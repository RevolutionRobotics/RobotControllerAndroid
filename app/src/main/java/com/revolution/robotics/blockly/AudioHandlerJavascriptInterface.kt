package com.revolution.robotics.blockly

import android.content.Context
import android.webkit.JavascriptInterface
import com.revolution.robotics.blockly.utils.SoundHelper
import org.revolution.blockly.view.jsInterface.BlocklyJavascriptInterface

class AudioHandlerJavascriptInterface(ctx: Context) : BlocklyJavascriptInterface {

    private var context: Context? = ctx
    private val soundHelper = SoundHelper()

    @JavascriptInterface
    fun playSound(file: String) {
        context?.let { soundHelper.playSound("sounds/$file.mp3", it) }
    }

    override fun release() {
        context = null
        soundHelper.release()
    }
}
