package com.revolution.bluetooth.communication

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.content.Context
import com.revolution.bluetooth.characteristic.RoboticsCharacteristic
import java.util.UUID

class BLEConnectionHandler : BluetoothGattCallback() {

    companion object {
        val SERVICE_ID_LIVE = UUID.fromString("d2d5558c-5b9d-11e9-8647-d663bd873d93")
        val SERVICE_ID_LONG = UUID.fromString("97148a03-5b9d-11e9-8647-d663bd873d93")
    }

    var device: BluetoothDevice? = null
    var gattConnection: BluetoothGatt? = null
    var gattLiveService: BluetoothGattService? = null
    var gattLongService: BluetoothGattService? = null

    var connectionListener: ConnectionListener? = null

    private val characteristics = mutableListOf<RoboticsCharacteristic>()

    fun connect(context: Context, device: BluetoothDevice, connectionListener: ConnectionListener) {
        this.device = device
        this.connectionListener = connectionListener
        device.connectGatt(context, true, this)
    }

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)
        connectionListener?.onConnectionStateChanged(ConnectionListener.ConnectionState.parseConnectionId(newState))
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        super.onServicesDiscovered(gatt, status)
        gattConnection = gatt
        gattLiveService = gattConnection?.getService(SERVICE_ID_LIVE)
        gattLongService = gattConnection?.getService(SERVICE_ID_LONG)

        characteristics.forEach {
            it.init(gatt)
        }
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic) {
        super.onCharacteristicChanged(gatt, characteristic)
        characteristics.find { it.id == characteristic.uuid }?.onCharacteristicChanged(characteristic)
    }

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) {
        super.onCharacteristicRead(gatt, characteristic, status)
        if (status == BluetoothGatt.GATT_SUCCESS) {
            characteristics.find { it.id == characteristic.uuid }?.onCharacteristicRead(characteristic)
        }
    }

    override fun onCharacteristicWrite(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic,
        status: Int
    ) {
        super.onCharacteristicWrite(gatt, characteristic, status)
        if (status == BluetoothGatt.GATT_SUCCESS) {
            characteristics.find { it.id == characteristic.uuid }?.onCharacteristicWrite(characteristic)
        }
    }

    override fun onMtuChanged(gatt: BluetoothGatt?, mtu: Int, status: Int) {
        super.onMtuChanged(gatt, mtu, status)
        if (status == BluetoothGatt.GATT_SUCCESS) {
            connectionListener?.onMTUChanged(mtu)
        }
    }

    override fun onReadRemoteRssi(gatt: BluetoothGatt?, rssi: Int, status: Int) {
        super.onReadRemoteRssi(gatt, rssi, status)
        if (status == BluetoothGatt.GATT_SUCCESS) {
            connectionListener?.onRSSIChanged(rssi)
        }
    }

    fun disconnect() {
        gattConnection?.close()
        gattConnection = null
        gattLongService = null
        gattLiveService = null
        device = null
    }
}
