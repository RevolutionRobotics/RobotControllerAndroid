package com.revolution.bluetooth.characteristic.long

import android.bluetooth.BluetoothGattCharacteristic
import com.revolution.bluetooth.characteristic.RoboticsCharacteristic
import com.revolution.bluetooth.communication.BLEConnectionHandler

abstract class LongRoboticsCharacteristic(handler: BLEConnectionHandler) : RoboticsCharacteristic(handler) {

    override fun getCharacteristic(): BluetoothGattCharacteristic? = handler.gattLongService?.getCharacteristic(id)
}
