package com.revolution.bluetooth.file

import android.net.Uri
import java.io.FileInputStream

class FileChunkHandler {

    private val chunks = mutableListOf<ByteArray>()
    var currentIndex = 0

    fun init(file: Uri, chunkLength: Int, firstByte: Byte) {
        chunks.clear()
        currentIndex = 0

        val fileInputStream = FileInputStream(file.path)
        val buffer = ByteArray(chunkLength - 1)
        var numRead: Int

        do {
            numRead = fileInputStream.read(buffer)
            if (numRead > 0) {
                ByteArray(chunkLength).apply {
                    this[0] = firstByte
                    buffer.forEachIndexed { index, byte ->
                        this[index + 1] = byte
                    }
                    chunks.add(this)
                }
            }
        } while (numRead != -1)
        fileInputStream.close()
    }

    fun getNextChunk(): ByteArray? {
        val currentChunk = chunks.getOrNull(currentIndex)
        currentIndex++
        return currentChunk
    }
}
