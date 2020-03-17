package com.revolution.robotics.features.bluetooth

import androidx.fragment.app.FragmentActivity
import com.revolution.robotics.core.utils.VersionNumber
import com.revolution.robotics.core.utils.dynamicPermissions.BluetoothConnectionFlowHelper
import com.revolution.robotics.features.mainmenu.settings.firmware.compatibility.FirmwareIncompatibleDialog
import org.kodein.di.Kodein
import org.kodein.di.erased.instance
import org.revolutionrobotics.bluetooth.android.communication.RoboticsConnectionStatusListener
import org.revolutionrobotics.bluetooth.android.communication.RoboticsDeviceConnector

@Suppress("TooManyFunctions")
class BluetoothManager(private var kodein: Kodein) : RoboticsConnectionStatusListener {

    private var activity: FragmentActivity? = null
    private var listeners = mutableListOf<BluetoothConnectionListener>()
    private var connectionFlowHelper: BluetoothConnectionFlowHelper? = null

    val bleConnectionHandler: RoboticsDeviceConnector by kodein.instance()

    var isConnected = false
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
        listener.onBluetoothConnectionStateChanged(isConnected, true)
    }

    fun unregisterListener(listener: BluetoothConnectionListener) {
        listeners.remove(listener)
    }

    override fun onConnectionStateChanged(connected: Boolean) {
        isConnected = connected
        if (connected) {
            getDeviceInfoService().apply {
                getSoftwareRevision({ version ->
                    val compatible = VersionNumber.parse(version) >= MINIMUM_FIRMWARE_VERSION
                    if (!compatible) {
                        activity?.supportFragmentManager?.let { FirmwareIncompatibleDialog().show(it) }
                    }
                    listeners.forEach { it.onBluetoothConnectionStateChanged(connected, compatible) }
                }, {
                    listeners.forEach { it.onBluetoothConnectionStateChanged(connected, true) }
                })
            }
        } else {
            listeners.forEach { it.onBluetoothConnectionStateChanged(connected, true) }
        }

    }

    fun disconnect() {
        bleConnectionHandler.disconnect().enqueue()
    }

    fun shutDown() {
        activity = null
        listeners.forEach { it.onBluetoothConnectionStateChanged(
            connected = false,
            firmwareCompatible = true
        ) }
        bleConnectionHandler.disconnect().enqueue()
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

    companion object {
        private val MINIMUM_FIRMWARE_VERSION = VersionNumber(0, 1, 957)
    }
}
