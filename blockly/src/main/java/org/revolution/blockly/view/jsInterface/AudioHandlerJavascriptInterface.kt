package org.revolution.blockly.view.jsInterface

import android.content.Context
import android.media.MediaPlayer
import android.webkit.JavascriptInterface

class AudioHandlerJavascriptInterface(ctx: Context) {

    private var context: Context? = ctx
    private val mediaPlayer = MediaPlayer()

    @JavascriptInterface
    fun playSound(file: String) {
        context?.let { context ->
            mediaPlayer.apply {
                reset()
                context.assets.openFd("sounds/$file.mp3").also { descriptor ->
                    setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                    descriptor.close()
                }

                prepare()
                start()
            }
        }
    }

    fun release() {
        context = null
        mediaPlayer.release()
    }
}
