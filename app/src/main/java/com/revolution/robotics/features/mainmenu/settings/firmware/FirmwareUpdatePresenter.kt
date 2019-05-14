package com.revolution.robotics.features.mainmenu.settings.firmware

import com.revolution.robotics.features.bluetooth.BluetoothConnectionListener
import com.revolution.robotics.features.bluetooth.BluetoothManager

class FirmwareUpdatePresenter(private val bluetoothManager: BluetoothManager) : FirmwareMvp.Presenter,
    BluetoothConnectionListener {

    override var view: FirmwareMvp.View? = null
    override var model: FirmwareUpdateViewModel? = null

    override fun register(view: FirmwareMvp.View, model: FirmwareUpdateViewModel?) {
        super.register(view, model)
        bluetoothManager.registerListener(this)
    }

    override fun unregister() {
        bluetoothManager.unregisterListener(this)
        super.unregister()
    }

    override fun onBluetoothConnectionStateChanged(connected: Boolean, serviceDiscovered: Boolean) {
        model?.hasConnectedRobot?.value = connected && serviceDiscovered
        if (connected && serviceDiscovered) {
            bluetoothManager.getDeviceInfoService().getSystemId({
                model?.robotName?.value = it
            }, {
                // TODO Error handling
            })
        }
    }

    override fun onConnectClicked() {
        bluetoothManager.startConnectionFlow()
    }

    override fun onRobotClicked() {
        // TODO show firmware update dialog
    }
}
