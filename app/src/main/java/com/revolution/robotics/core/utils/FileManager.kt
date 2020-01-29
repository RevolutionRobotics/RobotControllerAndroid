package com.revolution.robotics.core.utils

import android.content.Context
import java.io.File

class FileManager(
    private val context: Context
) {

    fun read(fileName: String): String = File(context.filesDir, fileName).readText()

    fun write(fileName: String, contents: String) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(contents.toByteArray())
        }
    }
}