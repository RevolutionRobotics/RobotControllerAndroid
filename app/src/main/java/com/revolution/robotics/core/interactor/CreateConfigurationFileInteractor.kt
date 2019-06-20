package com.revolution.robotics.core.interactor

import android.net.Uri
import androidx.core.net.toUri
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.features.play.FullControllerData
import com.revolution.robotics.features.play.configurationBuilder.ConfigurationBuilder
import java.io.File

class CreateConfigurationFileInteractor(private val contextProvider: ApplicationContextProvider) : Interactor<Uri>() {

    companion object {
        private const val CONFIGURATION_FILE_NAME = "config.json"
    }

    lateinit var controllerData: FullControllerData

    private val configurationBuilder = ConfigurationBuilder()

    override fun getData() =
        File("${contextProvider.applicationContext.filesDir}/$CONFIGURATION_FILE_NAME").apply {
            if (!exists()) {
                createNewFile()
            }
            writeText(configurationBuilder.createConfigurationJson(controllerData))
        }.toUri()
}
