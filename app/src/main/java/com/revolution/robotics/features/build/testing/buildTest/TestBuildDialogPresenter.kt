package com.revolution.robotics.features.build.testing.buildTest

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.features.bluetooth.BluetoothManager
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset

class TestBuildDialogPresenter(
    private val bluetoothManager: BluetoothManager,
    private val contextProvider: ApplicationContextProvider
) : TestBuildDialogMvp.Presenter {

    companion object {
        const val TEST_CODE_FILE_NAME = "test.py"
    }

    override var view: TestBuildDialogMvp.View? = null
    override var model: ViewModel? = null

    override fun sendTestCode(code: String) {
        val fileUri = saveTestKitFile(code)
        bluetoothManager.getConfigurationService().testKit(fileUri, {
            view?.activateBuildFace()
        }, {
            // TODO User error message when it's available
            view?.dismiss()
        })
    }

    private fun saveTestKitFile(code: String): Uri {
        val outputFile = File("${contextProvider.applicationContext.filesDir.absolutePath}/$TEST_CODE_FILE_NAME")
        FileOutputStream(outputFile).use {
            it.write(code.toByteArray(Charset.forName("UTF-8")))
        }
        return Uri.fromFile(outputFile)
    }
}
