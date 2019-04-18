package com.revolution.robotics.controller

import android.annotation.SuppressLint
import com.revolution.bluetooth.communication.RoboticsDeviceConnector
import com.revolution.bluetooth.discover.RoboticsDeviceDiscoverer
import com.revolution.bluetooth.domain.ConnectionState
import com.revolution.bluetooth.exception.BLEException
import com.revolution.bluetooth.service.RoboticsLiveControllerService
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider

class LiveControllerPresenter(private val applicationContextProvider: ApplicationContextProvider) :
    LiveControllerMvp.Presenter {

    override var view: LiveControllerMvp.View? = null
    override var model: LiveControllerViewModel? = null

    private val bleHandler = RoboticsDeviceConnector()
    private val bleDeviceDiscoverer = RoboticsDeviceDiscoverer()
    private var liveControllerService: RoboticsLiveControllerService? = null

    @SuppressLint("MissingPermission")
    override fun register(view: LiveControllerMvp.View, model: LiveControllerViewModel?) {
        super.register(view, model)
        model?.connectionState?.value = "Discovering"
        bleDeviceDiscoverer.discoverRobots(applicationContextProvider.applicationContext) { devices ->
            if (devices.isNotEmpty()) {
                bleHandler.connect(
                    applicationContextProvider.applicationContext,
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
        bleDeviceDiscoverer.stopDiscovering()
        liveControllerService?.stop()
        bleHandler.disconnect()
        super.unregister()
    }

    override fun onButtonClicked(index: Int) {
        liveControllerService?.negateButtonState(index)
    }

    override fun onXAxisChanged(value: Int) {
        liveControllerService?.updateXDirection(value)
    }

    override fun onYAxisChanged(value: Int) {
        liveControllerService?.updateYDirection(value)
    }
}
