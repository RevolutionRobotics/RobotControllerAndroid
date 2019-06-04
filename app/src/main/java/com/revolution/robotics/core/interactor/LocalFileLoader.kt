package com.revolution.robotics.core.interactor

import java.io.File

class LocalFileLoader : Interactor<String>() {

    lateinit var filePath: String

    override fun getData(): String = File(filePath).readText()
}
