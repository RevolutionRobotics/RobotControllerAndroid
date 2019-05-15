package com.revolution.robotics.features.bluetooth

interface BluetoothConnectionListener {
    fun onBluetoothConnectionStateChanged(connected: Boolean, serviceDiscovered: Boolean, manager: BluetoothManager)
}
