package com.revolution.robotics.features.build.testing.buildTest

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.interactor.firebase.FirebaseFileDownloader
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.shared.ErrorHandler

class TestBuildDialogPresenter(
    private val bluetoothManager: BluetoothManager,
    private val firebaseFileDownloader: FirebaseFileDownloader,
    private val errorHandler: ErrorHandler
) : TestBuildDialogMvp.Presenter {

    companion object {
        const val TEST_CODE_FILE_NAME = "test.py"
    }

    override var view: TestBuildDialogMvp.View? = null
    override var model: ViewModel? = null

    override fun sendTestCode(code: String) {
        firebaseFileDownloader.downloadFirestoreFile(TEST_CODE_FILE_NAME, code, onResponse = {
            uploadTestCode(it)
        }, onError = {
            errorHandler.onError(R.string.error_test_code_upload)
            view?.showTips()
        })
    }

    private fun uploadTestCode(fileUri: Uri) {
        bluetoothManager.getConfigurationService().testKit(fileUri,
            onSuccess = {
                view?.activateBuildFace()
            },
            onError = {
                errorHandler.onError(R.string.error_test_code_upload)
                view?.dismiss()
            })
    }
}
