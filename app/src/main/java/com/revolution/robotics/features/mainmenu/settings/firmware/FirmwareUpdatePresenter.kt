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

        onBluetoothConnectaionStateChanged(true)
    }

    override fun unregister() {
        bluetoothManager.unregisterListener(this)
        super.unregister()
    }

    override fun onBluetoothConnectaionStateChanged(connected: Boolean) {
        model?.hasConnectedRobot?.value = connected
        if (connected) {
            // TODO (Read robot name)
        }
    }

    override fun onConnectClicked() {
        bluetoothManager.startConnectionFlow()
    }

    override fun onRobotClicked() {
        // TODO show firmware update dialog
    }
}
