package com.revolution.bluetooth.characteristic.live

import android.bluetooth.BluetoothGattCharacteristic
import com.revolution.bluetooth.characteristic.RoboticsCharacteristic
import com.revolution.bluetooth.communication.BLEConnectionHandler

abstract class LiveRoboticsCharacteristic(handler: BLEConnectionHandler) : RoboticsCharacteristic(handler) {

    override fun getCharacteristic(): BluetoothGattCharacteristic? = handler.gattLiveService?.getCharacteristic(id)
}
