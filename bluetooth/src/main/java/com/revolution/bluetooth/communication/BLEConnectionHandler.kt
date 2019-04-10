package com.revolution.bluetooth.communication

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.content.Context
import java.util.UUID

class BLEConnectionHandler : BluetoothGattCallback() {

    companion object {
        const val SERVICE_ID = "TODO"
    }

    var device: BluetoothDevice? = null
    var gattConnection: BluetoothGatt? = null
    var gattService: BluetoothGattService? = null

    var connectionListener: ConnectionListener? = null

    fun connect(context: Context, device: BluetoothDevice, connectionListener: ConnectionListener) {
        this.device = device
        this.connectionListener = connectionListener
        device.connectGatt(context, true, this)
    }

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)
        connectionListener?.onConnectionStateChanged(ConnectionListener.ConnectionState.parseConnectionId(newState))
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        super.onServicesDiscovered(gatt, status)
        gattConnection = gatt
        gattService = gattConnection?.getService(UUID.fromString(SERVICE_ID))

    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
        super.onCharacteristicChanged(gatt, characteristic)
    }

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
        super.onCharacteristicRead(gatt, characteristic, status)
    }

    override fun onCharacteristicWrite(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic?,
        status: Int
    ) {
        super.onCharacteristicWrite(gatt, characteristic, status)
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
        gattService = null
        device = null
    }
}
