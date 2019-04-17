package com.revolution.bluetooth.communication

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.content.Context
import com.revolution.bluetooth.domain.ConnectionState
import com.revolution.bluetooth.domain.Device
import com.revolution.bluetooth.exception.BLEConnectionException
import com.revolution.bluetooth.exception.BLEException
import com.revolution.bluetooth.service.RoboticsBLEService
import com.revolution.bluetooth.service.RoboticsDeviceService
import com.revolution.bluetooth.threading.moveToUIThread
import java.util.UUID

class RoboticsDeviceConnector : BluetoothGattCallback() {

    companion object {
        val SERVICE_ID_LIVE: UUID = UUID.fromString("d2d5558c-5b9d-11e9-8647-d663bd873d93")
        val SERVICE_ID_LONG: UUID = UUID.fromString("97148a03-5b9d-11e9-8647-d663bd873d93")
    }

    var device: BluetoothDevice? = null
    var gattConnection: BluetoothGatt? = null
    var gattLiveService: BluetoothGattService? = null
    var gattLongService: BluetoothGattService? = null

    var onConnected: (() -> Unit)? = null
    var onDisconnected: (() -> Unit)? = null
    var onError: ((exception: BLEException) -> Unit)? = null

    val serivces = listOf<RoboticsBLEService>(RoboticsDeviceService())

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
                ConnectionState.parseConnectionId(newState).apply {
                    when (this) {
                        ConnectionState.CONNECTED -> onConnected?.invoke()
                        ConnectionState.DISCONNECTED -> onDisconnected?.invoke()
                        ConnectionState.DISCONNECTING, ConnectionState.CONNECTING -> Unit
                    }
                }
            } else {
                onError?.invoke(BLEConnectionException(status))
            }
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        gattConnection = gatt
        gattLiveService = gattConnection?.getService(SERVICE_ID_LIVE)
        gattLongService = gattConnection?.getService(SERVICE_ID_LONG)
        serivces.forEach {
            it.init(gatt)
        }
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic) {
        moveToUIThread {
            serivces.forEach { it.onCharacteristicChanged(gatt, characteristic) }
        }
    }

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) {
        moveToUIThread {
            serivces.forEach { it.onCharacteristicRead(gatt, characteristic, status) }
        }
    }

    override fun onCharacteristicWrite(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic,
        status: Int
    ) {
        moveToUIThread {
            serivces.forEach { it.onCharacteristicWrite(gatt, characteristic, status) }
        }
    }

    override fun onMtuChanged(gatt: BluetoothGatt?, mtu: Int, status: Int) = Unit

    override fun onReadRemoteRssi(gatt: BluetoothGatt?, rssi: Int, status: Int) = Unit

    fun disconnect() {
        serivces.forEach {
            it.disconnect()
        }
        gattConnection?.close()
        gattConnection = null
        gattLongService = null
        gattLiveService = null
        device = null
    }
}
