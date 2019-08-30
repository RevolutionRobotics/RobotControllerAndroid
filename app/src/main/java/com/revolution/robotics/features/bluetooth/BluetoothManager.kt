package com.revolution.robotics.features.bluetooth

import androidx.fragment.app.FragmentActivity
import com.revolution.robotics.core.utils.dynamicPermissions.BluetoothConnectionFlowHelper
import org.kodein.di.Kodein
import org.kodein.di.erased.instance
import org.revolutionrobotics.robotcontroller.bluetooth.communication.RoboticsConnectionStatusListener
import org.revolutionrobotics.robotcontroller.bluetooth.communication.RoboticsDeviceConnector

@Suppress("TooManyFunctions")
class BluetoothManager(private var kodein: Kodein) : RoboticsConnectionStatusListener {

    private var activity: FragmentActivity? = null
    private var listeners = mutableListOf<BluetoothConnectionListener>()
    private var connectionFlowHelper: BluetoothConnectionFlowHelper? = null

    val bleConnectionHandler: RoboticsDeviceConnector by kodein.instance()

    var isConnected = false
        private set
    var isServiceDiscovered = false
        private set

    fun init(activity: FragmentActivity) {
        this.activity = activity
        connectionFlowHelper = BluetoothConnectionFlowHelper(kodein).apply {
            init(activity.supportFragmentManager)
        }
        bleConnectionHandler.registerConnectionListener(this)
    }

    fun startConnectionFlow(robotName: String? = null) {
        activity?.let {
            connectionFlowHelper?.startConnectionFlow(it, robotName)
        }
    }

    fun registerListener(listener: BluetoothConnectionListener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
        listener.onBluetoothConnectionStateChanged(isConnected, isServiceDiscovered)
    }

    fun unregisterListener(listener: BluetoothConnectionListener) {
        listeners.remove(listener)
    }

    override fun onConnectionStateChanged(connected: Boolean, serviceDiscovered: Boolean) {
        isConnected = connected
        isServiceDiscovered = serviceDiscovered
        listeners.forEach { it.onBluetoothConnectionStateChanged(connected, serviceDiscovered) }
    }

    fun disconnect() {
        bleConnectionHandler.disconnect()
    }

    fun shutDown() {
        activity = null
        listeners.forEach { it.onBluetoothConnectionStateChanged(connected = false, serviceDiscovered = false) }
        bleConnectionHandler.disconnect()
        bleConnectionHandler.unregisterConnectionListener(this)
        isConnected = false
        connectionFlowHelper?.shutdown()
        connectionFlowHelper = null
    }

    fun getDeviceInfoService() = bleConnectionHandler.deviceService

    fun getBatteryInfoService() = bleConnectionHandler.batteryService

    fun getConfigurationService() = bleConnectionHandler.configurationService

    fun getLiveControllerService() = bleConnectionHandler.liveControllerService

    fun getMotorService() = bleConnectionHandler.motorService

    fun getSensorService() = bleConnectionHandler.sensorService
}
