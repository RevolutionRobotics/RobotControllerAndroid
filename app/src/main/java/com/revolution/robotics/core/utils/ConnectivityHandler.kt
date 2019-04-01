package com.revolution.robotics.core.utils

import android.bluetooth.BluetoothAdapter

class ConnectivityHandler {

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    fun isBluetoothEnabled() = bluetoothAdapter.isEnabled

}