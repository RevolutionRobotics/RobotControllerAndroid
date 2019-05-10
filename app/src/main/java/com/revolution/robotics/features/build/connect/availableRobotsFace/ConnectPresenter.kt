package com.revolution.robotics.features.build.connect.availableRobotsFace

import android.annotation.SuppressLint
import com.revolution.bluetooth.communication.RoboticsDeviceConnector
import com.revolution.bluetooth.discover.RoboticsDeviceDiscoverer
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem

@SuppressLint("MissingPermission")
class ConnectPresenter(private val applicationContextProvider: ApplicationContextProvider) : ConnectMvp.Presenter {

    override var view: ConnectMvp.View? = null
    override var model: ConnectViewModel? = null

    private val bleDeviceDiscoverer = RoboticsDeviceDiscoverer()
    private val bleHandler = RoboticsDeviceConnector()
    private var isConnectionInProgress = false

    override fun register(view: ConnectMvp.View, model: ConnectViewModel?) {
        super.register(view, model)

        bleDeviceDiscoverer.discoverRobots(applicationContextProvider.applicationContext) { devices ->
            model?.availableRobots?.value = devices.map { device -> ConnectRobotItem(device, this) }
            model?.isDiscovering?.set(devices.isNotEmpty())
        }
    }

    override fun unregister() {
        super.unregister()
        bleDeviceDiscoverer.stopDiscovering()
    }

    override fun onItemClicked(robot: ConnectRobotItem) {
        if (!isConnectionInProgress) {
            isConnectionInProgress = true
            robot.setSelected(true)
            bleDeviceDiscoverer.stopDiscovering()
            bleHandler.connect(applicationContextProvider.applicationContext, robot.device,
                onConnected = { view?.onConnectionSuccess() },
                onDisconnected = { view?.onConnectionError() },
                onError = { view?.onConnectionError() })
        }
    }
}
