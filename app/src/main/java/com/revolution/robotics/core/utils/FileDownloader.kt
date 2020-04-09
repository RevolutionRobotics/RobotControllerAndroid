package com.revolution.robotics.core.utils

import android.net.Uri
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class FileDownloader(
    private val applicationContextProvider: ApplicationContextProvider
) {
    fun download(src: URL, dst: String): Uri {
        val outputFile =
            File("${applicationContextProvider.applicationContext.filesDir}/${dst}")

        src.openStream().use { input ->
            FileOutputStream(outputFile).use { output ->
                input.copyTo(output)
            }
        }
        return Uri.fromFile(outputFile)
    }
}
