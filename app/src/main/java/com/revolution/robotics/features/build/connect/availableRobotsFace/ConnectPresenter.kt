package com.revolution.robotics.features.build.connect.availableRobotsFace

import android.annotation.SuppressLint
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem
import org.revolutionrobotics.bluetooth.android.communication.RoboticsDeviceConnector
import org.revolutionrobotics.bluetooth.android.discover.RoboticsDeviceDiscoverer

@SuppressLint("MissingPermission")
class ConnectPresenter(
    private val reporter: Reporter,
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
                addAll(devices.map { device -> ConnectRobotItem(device, this@ConnectPresenter) })
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
            bleHandler.connect(robot.device,
                onConnected = {
                    reporter.setUserProperty(Reporter.UserProperty.ROBOT_ID, robot.name)
                    view?.onConnectionSuccess()
                    isConnectionInProgress = false
                },
                onError = {
                    view?.onConnectionError()
                    isConnectionInProgress = false
                })
        }
    }
}
