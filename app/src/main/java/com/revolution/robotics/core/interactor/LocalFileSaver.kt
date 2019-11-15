package com.revolution.robotics.core.interactor

import android.net.Uri
import java.io.File

class LocalFileSaver : Interactor<Uri>() {

    lateinit var content: String
    lateinit var filePath: String

    override fun getData(): Uri {
        val file = File(filePath)
        file.writeText(content)
        return Uri.fromFile(file)
    }
}
