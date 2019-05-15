package com.revolution.robotics.features.mainmenu.settings.firmware.update

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.views.dialogs.DialogButton

class FirmwareUpdateDialogPresenter(
    private val bluetoothManager: BluetoothManager,
    private val resourceResolver: ResourceResolver
) : FirmwareUpdateMvp.Presenter {

    override var view: FirmwareUpdateMvp.View? = null
    override var model: ViewModel? = null

    private var infoViewModel: FirmwareUpdateInfoViewModel? = null

    override fun register(view: FirmwareUpdateMvp.View, model: ViewModel?) {
        super.register(view, model)
        infoViewModel = FirmwareUpdateInfoViewModel().apply {
            view.setInfoViewModel(this)
        }
        infoViewModel?.let { viewModel ->
            viewModel.updateTextVisible.value = true
            viewModel.updateText.value = resourceResolver.string(R.string.firmware_loading)
            bluetoothManager.getDeviceInfoService().apply {
                getSystemId({
                    viewModel.robotName.value = it
                }, ::readError)
                getFirmwareRevision({
                    viewModel.firmwareVersion.value = resourceResolver.string(R.string.firmware_current_version, it)
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
                    viewModel.updateTextVisible.value = false
                }, ::readError)
            }
        }
    }

    override fun onCheckForUpdatesClicked() {
        // TODO Load latest firmware version and add progress
        infoViewModel?.updateTextVisible?.value = true
        infoViewModel?.updateText?.value = resourceResolver.string(R.string.firmware_update_download_ready, "1.5.6")
        view?.activateInfoFace(DialogButton(R.string.firmware_update_download, R.drawable.ic_download_update, true) {
            // TODO Start update
            view?.activateLoadingFace()
        })
    }

    private fun readError(throwable: Throwable) {
        throwable.printStackTrace()
    }
}
