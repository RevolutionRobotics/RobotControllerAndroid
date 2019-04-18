package com.revolution.bluetooth.communication

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import com.revolution.bluetooth.domain.ConnectionState
import com.revolution.bluetooth.domain.Device
import com.revolution.bluetooth.exception.BLEConnectionException
import com.revolution.bluetooth.exception.BLEException
import com.revolution.bluetooth.service.RoboticsBatteryService
import com.revolution.bluetooth.service.RoboticsConfigurationService
import com.revolution.bluetooth.service.RoboticsDeviceService
import com.revolution.bluetooth.service.RoboticsLiveControllerService
import com.revolution.bluetooth.threading.moveToUIThread

class RoboticsDeviceConnector : BluetoothGattCallback() {

    private var device: BluetoothDevice? = null
    private var gattConnection: BluetoothGatt? = null

    private var onConnected: (() -> Unit)? = null
    private var onDisconnected: (() -> Unit)? = null
    private var onError: ((exception: BLEException) -> Unit)? = null

    private val services = setOf(
        RoboticsDeviceService(),
        RoboticsLiveControllerService(),
        RoboticsBatteryService(),
        RoboticsConfigurationService()
    )

    fun connect(
        context: Context,
        device: Device,
        onConnected: () -> Unit,
        onDisconnected: () -> Unit,
        onError: (exception: BLEException) -> Unit
    ) {
        this.device = device.bluetoothDevice
        this.onConnected = onConnected
        this.onDisconnected = onDisconnected
        this.onError = onError
        this.device?.connectGatt(context, true, this)
    }

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        moveToUIThread {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                when (ConnectionState.parseConnectionId(newState)) {
                    ConnectionState.CONNECTED -> onConnected?.invoke()
                    ConnectionState.DISCONNECTED -> onDisconnected?.invoke()
                    ConnectionState.DISCONNECTING, ConnectionState.CONNECTING -> Unit
                }
                gatt?.discoverServices()
            } else {
                onError?.invoke(BLEConnectionException(status))
            }
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        gattConnection = gatt
        services.forEach {
            it.init(gatt)
        }
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic) {
        moveToUIThread {
            services.forEach { it.onCharacteristicChanged(gatt, characteristic) }
        }
    }

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) {
        moveToUIThread {
            services.forEach { it.onCharacteristicRead(gatt, characteristic, status) }
        }
    }

    override fun onCharacteristicWrite(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic,
        status: Int
    ) {
        moveToUIThread {
            services.forEach { it.onCharacteristicWrite(gatt, characteristic, status) }
        }
    }

    fun getDeviceService() = services.first { it is RoboticsDeviceService } as RoboticsDeviceService

    fun getLiveControllerService(): RoboticsLiveControllerService =
        services.first { it is RoboticsLiveControllerService } as RoboticsLiveControllerService

    fun getBatteryService(): RoboticsBatteryService =
        services.first { it is RoboticsBatteryService } as RoboticsBatteryService

    fun getConfigurationService(): RoboticsConfigurationService =
        services.first { it is RoboticsConfigurationService } as RoboticsConfigurationService

    override fun onMtuChanged(gatt: BluetoothGatt?, mtu: Int, status: Int) = Unit

    override fun onReadRemoteRssi(gatt: BluetoothGatt?, rssi: Int, status: Int) = Unit

    fun disconnect() {
        services.forEach {
            it.disconnect()
        }
        gattConnection?.disconnect()
        gattConnection?.close()
        gattConnection = null
        device = null
    }
}
