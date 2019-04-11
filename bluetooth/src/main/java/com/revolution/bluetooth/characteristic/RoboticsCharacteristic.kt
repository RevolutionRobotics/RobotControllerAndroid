package com.revolution.bluetooth.characteristic

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.revolution.bluetooth.communication.BLEConnectionHandler
import java.util.UUID

abstract class RoboticsCharacteristic(protected val handler: BLEConnectionHandler) {

    fun read() {
        getCharacteristic()?.let {
            handler.gattConnection?.readCharacteristic(it)
        }
    }

    fun write(data: ByteArray) {
        getCharacteristic()?.let { characteristic ->
            characteristic.value = data
            handler.gattConnection?.writeCharacteristic(characteristic)
        }
    }

    fun subscribeForUpdates() {
        getCharacteristic()?.let { characteristic ->
            handler.gattConnection?.setCharacteristicNotification(characteristic, true)
            val descriptor = characteristic.getDescriptor(configId)
            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            handler.gattConnection?.writeDescriptor(descriptor)
        }
    }

    fun unubscribeForUpdates() {
        getCharacteristic()?.let { characteristic ->
            handler.gattConnection?.setCharacteristicNotification(characteristic, true)
            val descriptor = characteristic.getDescriptor(configId)
            descriptor.value = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
            handler.gattConnection?.writeDescriptor(descriptor)
        }
    }

    abstract fun getCharacteristic(): BluetoothGattCharacteristic?

    abstract val id: UUID
    abstract val configId: UUID

    abstract fun init(bluetoothGatt: BluetoothGatt)
    abstract fun onCharacteristicChanged(characteristic: BluetoothGattCharacteristic)
    abstract fun onCharacteristicRead(characteristic: BluetoothGattCharacteristic)
    abstract fun onCharacteristicWrite(characteristic: BluetoothGattCharacteristic)
}
