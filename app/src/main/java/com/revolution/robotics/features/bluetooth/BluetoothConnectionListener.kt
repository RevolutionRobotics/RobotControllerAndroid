package com.revolution.robotics.features.bluetooth

interface BluetoothConnectionListener {
    fun onBluetoothConnectionStateChanged(connected: Boolean, firmwareCompatible: Boolean)
}
