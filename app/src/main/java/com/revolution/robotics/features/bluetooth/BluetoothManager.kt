package com.revolution.robotics.features.bluetooth

import androidx.fragment.app.FragmentActivity
import com.revolution.bluetooth.communication.RoboticsConnectionStatusListener
import com.revolution.bluetooth.communication.RoboticsDeviceConnector
import com.revolution.robotics.core.utils.dynamicPermissions.BluetoothConnectionFlowHelper
import org.kodein.di.Kodein
import org.kodein.di.erased.instance

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

    fun startConnectionFlow() {
        activity?.let {
            connectionFlowHelper?.startConnectionFlow(it)
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

    fun shutDown() {
        activity = null
        listeners.forEach { it.onBluetoothConnectionStateChanged(false, false) }
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
