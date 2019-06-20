package com.revolution.robotics.features.build.testing

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.interactor.PortTestFileCreatorInteractor
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.shared.ErrorHandler

class TestPresenter(
    private val portTestFileCreatorInteractor: PortTestFileCreatorInteractor,
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler
) : TestMvp.Presenter {

    companion object {
        private const val TEST_FOLDER = "testscripts"
    }

    override var view: TestMvp.View? = null
    override var model: ViewModel? = null

    override fun uploadTest(assetName: String, replaceablePairs: List<Pair<String, String>>) {
        portTestFileCreatorInteractor.assetFileName = "$TEST_FOLDER/$assetName.py"
        portTestFileCreatorInteractor.replaceablePairs = replaceablePairs
        portTestFileCreatorInteractor.execute(onResponse = {
            sendConfiguration(it)
        }, onError = {
            view?.onTestUploaded()
            errorHandler.onError()
        })
    }

    private fun sendConfiguration(uri: Uri) {
        bluetoothManager.getConfigurationService().testKit(uri,
            onSuccess = {
                view?.onTestUploaded()
            },
            onError = {
                view?.onTestUploaded()
                errorHandler.onError()
            })
    }
}
