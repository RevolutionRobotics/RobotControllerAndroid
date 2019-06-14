package com.revolution.robotics.features.build.testing

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.shared.ErrorHandler
import java.io.File

class TestPresenter(
    private val applicationContextProvider: ApplicationContextProvider,
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler
) : TestMvp.Presenter {

    companion object {
        private const val TEST_FOLDER = "testscripts"
    }

    override var view: TestMvp.View? = null
    override var model: ViewModel? = null

    override fun uploadTest(assetName: String) {
        val testFileUri = createFileFromAsset("$TEST_FOLDER/$assetName.py")
        bluetoothManager.getConfigurationService().testKit(testFileUri,
            onSuccess = {
                view?.onTestUploaded()
            },
            onError = {
                view?.onTestUploaded()
                errorHandler.onError()
            })
    }

    private fun createFileFromAsset(assetFileName: String): Uri {
        val stream = applicationContextProvider.applicationContext.assets.open(assetFileName)
        // TODO replace tokens here
        val fileContents = stream.bufferedReader().useLines { it.joinToString("\n") }
        return File("${applicationContextProvider.applicationContext.filesDir}/test.py").apply {
            if (!exists()) {
                createNewFile()
            }
            writeText(fileContents)
        }.toUri()
    }
}
