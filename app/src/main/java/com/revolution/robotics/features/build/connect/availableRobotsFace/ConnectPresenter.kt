package com.revolution.robotics.features.build.connect.availableRobotsFace

import android.annotation.SuppressLint
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.utils.dynamicPermissions.BluetoothConnectionFlowHelper
import com.revolution.robotics.features.build.connect.ConnectDialog
import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem
import org.revolutionrobotics.robotcontroller.bluetooth.communication.RoboticsDeviceConnector
import org.revolutionrobotics.robotcontroller.bluetooth.discover.RoboticsDeviceDiscoverer

@SuppressLint("MissingPermission")
class ConnectPresenter(
    private val applicationContextProvider: ApplicationContextProvider,
    private val bleHandler: RoboticsDeviceConnector
) : ConnectMvp.Presenter {

    override var view: ConnectMvp.View? = null
    override var model: ConnectViewModel? = null

    private val bleDeviceDiscoverer = RoboticsDeviceDiscoverer()
    private var isConnectionInProgress = false

    override fun register(view: ConnectMvp.View, model: ConnectViewModel?) {
        super.register(view, model)

        bleDeviceDiscoverer.discoverRobots(applicationContextProvider.applicationContext) { devices ->
            model?.availableRobots?.value = HashSet<ConnectRobotItem>().apply {
                model?.availableRobots?.value?.let { addAll(it) }
                addAll(devices.map { device ->
                    val item = ConnectRobotItem(device, this@ConnectPresenter)
                    if (device.name == ConnectDialog.autoConnectDeviceName) {
                        onItemClicked(item)
                    }
                    item
                })
            }
            model?.isDiscovering?.set(model.availableRobots.value?.isEmpty() == true)
        }
    }

    override fun unregister(view: ConnectMvp.View?) {
        bleDeviceDiscoverer.stopDiscovering()
        super.unregister(view)
    }

    override fun onItemClicked(robot: ConnectRobotItem) {
        if (!isConnectionInProgress) {
            bleDeviceDiscoverer.stopDiscovering()
            isConnectionInProgress = true
            robot.setSelected(true)
            bleHandler.connect(applicationContextProvider.applicationContext, robot.device,
                onConnected = {
                    view?.onConnectionSuccess()
                    isConnectionInProgress = false
                },
                onDisconnected = {
                    robot.setSelected(false)
                    isConnectionInProgress = false
                },
                onError = {
                    view?.onConnectionError()
                    isConnectionInProgress = false
                })
        }
    }
}
