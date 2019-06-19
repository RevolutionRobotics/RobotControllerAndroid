package com.revolution.robotics.features.mainmenu.settings.firmware

import com.revolution.robotics.features.bluetooth.BluetoothConnectionListener
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.shared.ErrorHandler

class FirmwareUpdatePresenter(
    private val bluetoothManager: BluetoothManager,
    private val errorHandler: ErrorHandler
) : FirmwareMvp.Presenter,
    BluetoothConnectionListener {

    override var view: FirmwareMvp.View? = null
    override var model: FirmwareUpdateViewModel? = null

    override fun register(view: FirmwareMvp.View, model: FirmwareUpdateViewModel?) {
        super.register(view, model)
        bluetoothManager.registerListener(this)
    }

    override fun unregister(view: FirmwareMvp.View?) {
        bluetoothManager.unregisterListener(this)
        super.unregister(view)
    }

    override fun onBluetoothConnectionStateChanged(connected: Boolean, serviceDiscovered: Boolean) {
        model?.hasConnectedRobot?.value = connected && serviceDiscovered
        if (connected && serviceDiscovered) {
            bluetoothManager.getDeviceInfoService().getSystemId(
                onCompleted = {
                    model?.robotName?.value = it
                }, onError = {
                    errorHandler.onError()
                })
        }
    }

    override fun onConnectClicked() {
        bluetoothManager.startConnectionFlow()
    }

    override fun onRobotClicked() {
        view?.showFirmwareUpdateDialog()
    }
}
