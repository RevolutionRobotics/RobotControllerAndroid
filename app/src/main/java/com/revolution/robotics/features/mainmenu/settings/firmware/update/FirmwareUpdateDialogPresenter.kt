package com.revolution.robotics.features.mainmenu.settings.firmware.update

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.analytics.reportUploadedToBrain
import com.revolution.robotics.core.domain.remote.Firmware
import com.revolution.robotics.core.interactor.api.GetFirmwareInteractor
import com.revolution.robotics.core.interactor.firebase.FirebaseFileDownloader
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.shared.ErrorHandler
import com.revolution.robotics.views.dialogs.DialogButton
import java.lang.RuntimeException

class FirmwareUpdateDialogPresenter(
    private val interactor: GetFirmwareInteractor,
    private val fileDownloader: FirebaseFileDownloader,
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler,
    private val resourceResolver: ResourceResolver,
    private val reporter: Reporter
) : FirmwareUpdateMvp.Presenter {

    companion object {
        private const val FIRMWARE_FILENAME = "firmware.tar.gz"
    }

    override var view: FirmwareUpdateMvp.View? = null
    override var model: ViewModel? = null

    private var infoViewModel: FirmwareUpdateInfoViewModel? = null
    private var isUpdateFlowStarted = false
    private var firmwareUpdateInProgress = false
    private var latestFirmware: Firmware? = null

    override fun register(view: FirmwareUpdateMvp.View, model: ViewModel?) {
        super.register(view, model)
        isUpdateFlowStarted = false
        firmwareUpdateInProgress = false
        infoViewModel = FirmwareUpdateInfoViewModel().apply {
            view.setInfoViewModel(this)
        }
        infoViewModel?.let { viewModel ->
            viewModel.updateTextVisible.value = false
            viewModel.infoTextsVisible.value = false
            viewModel.loadingTextVisible.value = true
            viewModel.updateText.value = resourceResolver.string(R.string.firmware_loading)
            bluetoothManager.getDeviceInfoService().apply {
                getSystemId({
                    viewModel.robotName.value = it
                }, ::readError)
                getHardwareRevision({
                    viewModel.hardwareVersion.value = resourceResolver.string(R.string.firmware_hardware_version, it)
                }, ::readError)
                getSoftwareRevision({ version ->
                    viewModel.softwareVersion.value =
                        resourceResolver.string(R.string.firmware_software_version, version)
                    viewModel.firmwareVersionCode = version
                    viewModel.firmwareVersion.value =
                        resourceResolver.string(R.string.firmware_current_version, version)
                }, ::readError)
                getSerialNumber({
                    viewModel.serialNumber.value = resourceResolver.string(R.string.firmware_serial_number, it)
                }, ::readError)
                getManufacturerName({
                    viewModel.manufacturer.value = resourceResolver.string(R.string.firmware_manufacturer_name, it)
                }, ::readError)
                getModelNumber({
                    viewModel.modelNumber.value = resourceResolver.string(R.string.firmware_model_number, it)
                }, ::readError)
            }

            bluetoothManager.getBatteryInfoService().apply {
                getPrimaryBattery({
                    viewModel.batteryMain.value = resourceResolver.string(R.string.firmware_main_battery, it.toString())
                }, ::readError)
                getMotorBattery({ percentage ->
                    viewModel.batteryMotor.value =
                        resourceResolver.string(R.string.firmware_motor_battery, percentage.toString())

                    if (!isUpdateFlowStarted) {
                        viewModel.updateTextVisible.value = false
                        viewModel.loadingTextVisible.value = false
                        viewModel.infoTextsVisible.value = true
                    }
                }, ::readError)
            }
        }
    }

    override fun onCheckForUpdatesClicked() {
        if (!resourceResolver.isInternetConnectionAvailable()) {
            errorHandler.onError(RuntimeException(), R.string.error_internet)
            return
        }

        isUpdateFlowStarted = true
        interactor.execute { firmware ->
            latestFirmware = firmware
            infoViewModel?.updateTextVisible?.value = true
            infoViewModel?.loadingTextVisible?.value = false
            infoViewModel?.infoTextsVisible?.value = false

            if (firmware?.filename == infoViewModel?.firmwareVersionCode) {
                onLatestFirmwareUsed()
            } else {
                onFirmwareUpdateAvailable()
            }
        }
    }

    private fun onFirmwareUpdateAvailable() {
        infoViewModel?.updateText?.value =
            resourceResolver.string(R.string.firmware_update_download_ready, latestFirmware?.filename ?: "")
        view?.activateInfoFace(
            DialogButton(R.string.firmware_update_download, R.drawable.ic_download_update, true) {
                updateFirmware()
            })
    }

    private fun onLatestFirmwareUsed() {
        infoViewModel?.updateText?.value = resourceResolver.string(R.string.firmware_update_latest_used)
        view?.activateInfoFace(
            DialogButton(R.string.done, R.drawable.ic_check, true) {
                view?.closeDialog()
            }
        )
    }

    override fun retryFirmwareUpdate() {
        updateFirmware()
    }

    override fun stopFirmwareUpdate() {
        if (isUpdateFlowStarted) {
            bluetoothManager.getConfigurationService().stop()
        }
    }

    override fun onCloseClicked() {
        if (firmwareUpdateInProgress) {
            view?.activateConfirmationFace()
        } else {
            view?.closeDialog()
        }
    }

    @Suppress("UnusedPrivateMember")
    private fun readError(throwable: Throwable) {
        if (!isUpdateFlowStarted) {
            errorHandler.onError(throwable)
        }
    }

    private fun updateFirmware() {
        firmwareUpdateInProgress = true
        val startTime = System.currentTimeMillis()
        latestFirmware?.url?.let { firmwareUrl ->
            view?.activateLoadingFace()
            fileDownloader.downloadFirestoreFile(FIRMWARE_FILENAME, firmwareUrl, { firmwareUri ->
                bluetoothManager.getConfigurationService().updateFramework(firmwareUri,
                    onSuccess = {
                        firmwareUpdateInProgress = false
                        reporter.reportEvent(Reporter.Event.UPDATE_FIRMWARE, Bundle().apply {
                            putString(Reporter.Parameter.VERSION.parameterName, latestFirmware?.filename)
                        })
                        reportUploadedToBrain(reporter, "firmware", firmwareUri, startTime)
                        isUpdateFlowStarted = false
                        view?.activateSuccessFace()
                    },
                    onError = {
                        firmwareUpdateInProgress = false
                        view?.activateErrorFace()
                    })
            }, {
                view?.activateErrorFace()
            })
        }
    }
}
