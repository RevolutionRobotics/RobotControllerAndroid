package com.revolution.robotics.controller

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import com.revolution.bluetooth.characteristic.live.PeriodicControllerCharacteristic
import com.revolution.bluetooth.communication.RoboticsDeviceConnector
import com.revolution.bluetooth.communication.ConnectionListener
import com.revolution.bluetooth.communication.RoboticCharacteristicListener
import com.revolution.bluetooth.discover.RoboticsDeviceDiscoverer
import com.revolution.bluetooth.discover.ScanResultListener
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class LiveControllerPresenter(private val resourceResolver: ResourceResolver) : LiveControllerMvp.Presenter,
    ScanResultListener, ConnectionListener {

    override var view: LiveControllerMvp.View? = null
    override var model: LiveControllerViewModel? = null

    private val bleHandler = RoboticsDeviceConnector()
    private val bleDeviceDiscoverer = RoboticsDeviceDiscoverer()
    private var liveControllerCharachteristic: PeriodicControllerCharacteristic? = null

    @SuppressLint("MissingPermission")
    override fun register(view: LiveControllerMvp.View, model: LiveControllerViewModel?) {
        super.register(view, model)
        bleDeviceDiscoverer.discoverRobots(resourceResolver.context, this)
    }

    override fun unregister() {
        super.unregister()
        liveControllerCharachteristic?.stop()
        bleHandler.disconnect()
    }

    override fun onScanResult(scanResult: List<ScanResult>) {
        if (scanResult.isNotEmpty()) {
            bleHandler.connect(
                resourceResolver.context,
                scanResult.first().device,
                this,
                RoboticCharacteristicListener()
            )
            bleDeviceDiscoverer.stopDiscovering()
        }
    }

    override fun buttonActionUp(buttonIndex: Int) {
        liveControllerCharachteristic?.changeButtonState(buttonIndex, false)
    }

    override fun buttonActionDown(buttonIndex: Int) {
        liveControllerCharachteristic?.changeButtonState(buttonIndex, true)
    }

    override fun onXAxisChanged(value: Int) {
        liveControllerCharachteristic?.updateXDirection(value)
    }

    override fun onYAxisChanged(value: Int) {
        liveControllerCharachteristic?.updateYDirection(value)
    }

    override fun onConnectionStateChanged(connectionState: ConnectionListener.ConnectionState) {
        model?.connectionState?.value = connectionState.name
        if (connectionState == ConnectionListener.ConnectionState.CONNECTED) {
            liveControllerCharachteristic =
                bleHandler.getCharacteristicHandler(PeriodicControllerCharacteristic.CHARACHTERISTIC_ID)
            liveControllerCharachteristic?.start()
        }
    }

    override fun onRSSIChanged(rssi: Int) = Unit

    override fun onMTUChanged(mtu: Int) = Unit
}