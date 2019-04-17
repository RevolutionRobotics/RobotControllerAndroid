package com.revolution.robotics.controller

import android.annotation.SuppressLint
import com.revolution.bluetooth.communication.RoboticsDeviceConnector
import com.revolution.bluetooth.discover.RoboticsDeviceDiscoverer
import com.revolution.bluetooth.domain.ConnectionState
import com.revolution.bluetooth.exception.BLEException
import com.revolution.bluetooth.service.RoboticsLiveControllerService
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class LiveControllerPresenter(private val resourceResolver: ResourceResolver) : LiveControllerMvp.Presenter {

    override var view: LiveControllerMvp.View? = null
    override var model: LiveControllerViewModel? = null

    private val bleHandler = RoboticsDeviceConnector()
    private val bleDeviceDiscoverer = RoboticsDeviceDiscoverer()
    private var liveControllerService: RoboticsLiveControllerService? = null

    @SuppressLint("MissingPermission")
    override fun register(view: LiveControllerMvp.View, model: LiveControllerViewModel?) {
        super.register(view, model)
        bleDeviceDiscoverer.discoverRobots(resourceResolver.context) { devices ->
            if (devices.isNotEmpty()) {
                bleHandler.connect(
                    resourceResolver.context,
                    devices.first(),
                    ::onConnected,
                    ::onDisconnected,
                    ::onError
                )
                bleDeviceDiscoverer.stopDiscovering()
            }
        }
    }

    private fun onConnected() {
        model?.connectionState?.value = ConnectionState.CONNECTED.name
        liveControllerService = bleHandler.getLiveControllerService()
        liveControllerService?.start()
    }

    private fun onDisconnected() {
        liveControllerService?.stop()
        model?.connectionState?.value = ConnectionState.DISCONNECTED.name
    }

    private fun onError(exception: BLEException) {
        liveControllerService?.stop()
        model?.connectionState?.value = exception.message
    }

    override fun unregister() {
        super.unregister()
        liveControllerService?.stop()
        bleHandler.disconnect()
    }

    override fun buttonActionUp(buttonIndex: Int) {
        liveControllerService?.changeButtonState(buttonIndex, false)
    }

    override fun buttonActionDown(buttonIndex: Int) {
        liveControllerService?.changeButtonState(buttonIndex, true)
    }

    override fun onXAxisChanged(value: Int) {
        liveControllerService?.updateXDirection(value)
    }

    override fun onYAxisChanged(value: Int) {
        liveControllerService?.updateYDirection(value)
    }
}