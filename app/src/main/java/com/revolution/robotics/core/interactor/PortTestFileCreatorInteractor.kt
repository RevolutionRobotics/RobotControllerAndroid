package com.revolution.robotics.core.interactor

import android.net.Uri
import androidx.core.net.toUri
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import java.io.File

class PortTestFileCreatorInteractor(private val applicationContextProvider: ApplicationContextProvider) :
    Interactor<Uri>() {

    lateinit var replaceablePairs: List<Pair<String, String>>
    lateinit var assetFileName: String

    override fun getData(): Uri {
        val stream = applicationContextProvider.applicationContext.assets.open(assetFileName)
        var fileContents = stream.bufferedReader().readText()
        replaceablePairs.forEach {
            fileContents = fileContents.replace(it.first, it.second)
        }

        return File("${applicationContextProvider.applicationContext.filesDir}/test.py").apply {
            if (!exists()) {
                createNewFile()
            }
            writeText(fileContents)
        }.toUri()
    }
}
