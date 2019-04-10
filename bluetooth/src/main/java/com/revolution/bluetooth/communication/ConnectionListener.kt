package com.revolution.bluetooth.communication

import android.bluetooth.BluetoothProfile

interface ConnectionListener {

    enum class ConnectionState(val id: Int) {
        CONNECTING(BluetoothProfile.STATE_CONNECTING),
        CONNECTED(BluetoothProfile.STATE_CONNECTING),
        DISCONNECTING(BluetoothProfile.STATE_CONNECTING),
        DISCONNECTED(BluetoothProfile.STATE_CONNECTING);

        companion object {
            fun parseConnectionId(id: Int): ConnectionState =
                ConnectionState.values().find { it.id == id } ?: throw IllegalArgumentException("Invalid id $id")
        }
    }

    fun onConnectionStateChanged(connectionState: ConnectionState)
    fun onRSSIChanged(rssi: Int)
    fun onMTUChanged(mtu: Int)
}
