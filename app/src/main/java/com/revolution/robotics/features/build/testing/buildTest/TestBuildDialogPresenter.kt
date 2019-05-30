package com.revolution.robotics.features.build.testing.buildTest

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.interactor.firebase.FirebaseFileDownloader
import com.revolution.robotics.features.bluetooth.BluetoothManager

class TestBuildDialogPresenter(
    private val bluetoothManager: BluetoothManager,
    private val firebaseFileDownloader: FirebaseFileDownloader
) : TestBuildDialogMvp.Presenter {

    companion object {
        const val TEST_CODE_FILE_NAME = "test.py"
    }

    override var view: TestBuildDialogMvp.View? = null
    override var model: ViewModel? = null

    override fun sendTestCode(code: String) {
        firebaseFileDownloader.downloadFirestoreFile(TEST_CODE_FILE_NAME, code, {
            uploadTestCode(it)
        }, {
            // TODO User error message when it's available
            view?.dismiss()
        })
    }

    private fun uploadTestCode(fileUri: Uri) {
        bluetoothManager.getConfigurationService().testKit(fileUri, {
            view?.activateBuildFace()
        }, {
            // TODO User error message when it's available
            view?.dismiss()
        })
    }
}
