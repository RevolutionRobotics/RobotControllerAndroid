package com.revolution.bluetooth.communication

import android.bluetooth.BluetoothProfile
import androidx.annotation.MainThread

interface ConnectionListener {

    enum class ConnectionState(val id: Int) {
        CONNECTING(BluetoothProfile.STATE_CONNECTING),
        CONNECTED(BluetoothProfile.STATE_CONNECTED),
        DISCONNECTING(BluetoothProfile.STATE_DISCONNECTING),
        DISCONNECTED(BluetoothProfile.STATE_DISCONNECTED);

        companion object {
            fun parseConnectionId(id: Int): ConnectionState =
                ConnectionState.values().find { it.id == id } ?: throw IllegalArgumentException("Invalid id $id")
        }
    }

    @MainThread
    fun onConnectionStateChanged(connectionState: ConnectionState)
    @MainThread
    fun onRSSIChanged(rssi: Int)
    @MainThread
    fun onMTUChanged(mtu: Int)
}
