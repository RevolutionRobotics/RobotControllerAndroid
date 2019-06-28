package com.revolution.robotics.features.build.testing.buildTest

import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.interactor.LocalFileSaver
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.shared.ErrorHandler

class TestBuildDialogPresenter(
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler,
    private val localFileSaver: LocalFileSaver,
    private val applicationContextProvider: ApplicationContextProvider
) : TestBuildDialogMvp.Presenter {

    companion object {
        const val TEST_CODE_FILE_NAME = "test.py"
    }

    override var view: TestBuildDialogMvp.View? = null
    override var model: ViewModel? = null

    override fun sendTestCode(code: String) {
        localFileSaver.content = String(Base64.decode(code, Base64.NO_WRAP))
        localFileSaver.filePath = "${applicationContextProvider.applicationContext.filesDir}/$TEST_CODE_FILE_NAME"
        localFileSaver.execute {
            uploadTestCode(it)
        }
    }

    private fun uploadTestCode(fileUri: Uri) {
        bluetoothManager.getConfigurationService().testKit(fileUri,
            onSuccess = {
                view?.activateBuildFace()
            },
            onError = {
                errorHandler.onError(R.string.error_test_code_upload)
                view?.showTips()
            })
    }
}
