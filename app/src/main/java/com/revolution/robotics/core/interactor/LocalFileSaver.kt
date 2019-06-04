package com.revolution.robotics.core.interactor

import java.io.File

class LocalFileSaver : Interactor<Unit>() {

    lateinit var content: String
    lateinit var filePath: String

    override fun getData() {
        File(filePath).writeText(content)
    }
}
