package com.revolution.robotics.features.mainmenu.settings.firmware.update

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Firmware
import com.revolution.robotics.core.interactor.firebase.FirebaseFileDownloader
import com.revolution.robotics.core.interactor.firebase.FirmwareInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.shared.ErrorHandler
import com.revolution.robotics.views.dialogs.DialogButton

class FirmwareUpdateDialogPresenter(
    private val interactor: FirmwareInteractor,
    private val fileDownloader: FirebaseFileDownloader,
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler,
    private val resourceResolver: ResourceResolver
) : FirmwareUpdateMvp.Presenter {

    companion object {
        private const val FIRMWARE_FILENAME = "firmware.tar.gz"
    }

    override var view: FirmwareUpdateMvp.View? = null
    override var model: ViewModel? = null

    private var infoViewModel: FirmwareUpdateInfoViewModel? = null
    private var isUpdateFlowStarted = false
    private var latestFirmware: Firmware? = null

    override fun register(view: FirmwareUpdateMvp.View, model: ViewModel?) {
        super.register(view, model)
        isUpdateFlowStarted = false
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
                getFirmwareRevision({ version ->
                    viewModel.firmwareVersionCode = version
                    viewModel.firmwareVersion.value =
                        resourceResolver.string(R.string.firmware_current_version, version)
                }, ::readError)
                getHardwareRevision({
                    viewModel.hardwareVersion.value = resourceResolver.string(R.string.firmware_hardware_version, it)
                }, ::readError)
                getSoftwareRevision({
                    viewModel.softwareVersion.value = resourceResolver.string(R.string.firmware_software_version, it)
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
        isUpdateFlowStarted = true
        interactor.execute { firmware ->
            latestFirmware = firmware
            infoViewModel?.updateTextVisible?.value = true
            infoViewModel?.loadingTextVisible?.value = false
            infoViewModel?.infoTextsVisible?.value = false

            if (firmware.filename == infoViewModel?.firmwareVersionCode) {
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

    @Suppress("UnusedPrivateMember")
    private fun readError(throwable: Throwable) {
        if (!isUpdateFlowStarted) {
            errorHandler.onError()
        }
    }

    private fun updateFirmware() {
        latestFirmware?.url?.let { firmwareUrl ->
            view?.activateLoadingFace()
            fileDownloader.downloadFirestoreFile(FIRMWARE_FILENAME, firmwareUrl) { firmwareUri ->
                bluetoothManager.getConfigurationService().updateFramework(firmwareUri,
                    onSuccess = {
                        isUpdateFlowStarted = false
                        view?.activateSuccessFace()
                    },
                    onError = {
                        view?.activateErrorFace()
                    })
            }
        }
    }
}
