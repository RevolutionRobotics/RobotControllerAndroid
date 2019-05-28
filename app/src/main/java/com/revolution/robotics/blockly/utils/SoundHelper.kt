package com.revolution.robotics.blockly.utils

import android.content.Context
import android.media.MediaPlayer

class SoundHelper {

    private val mediaPlayer = MediaPlayer()

    fun playSound(file: String, context: Context) {
        mediaPlayer.apply {
            reset()
            context.assets.openFd(file).also { descriptor ->
                setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                descriptor.close()
            }

            prepare()
            start()
        }
    }

    fun release() {
        mediaPlayer.release()
    }
}
