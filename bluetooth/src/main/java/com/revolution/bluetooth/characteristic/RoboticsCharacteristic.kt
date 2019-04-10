package com.revolution.bluetooth.characteristic

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.revolution.bluetooth.communication.BLEConnectionHandler
import java.util.UUID

abstract class RoboticsCharacteristic(private val handler: BLEConnectionHandler) {

    fun read() {
        getCharacteristic()?.let {
            handler.gattConnection?.readCharacteristic(it)
        }
    }

    fun write(data: ByteArray) {
        getCharacteristic()?.let {
            it.value = data
            handler.gattConnection?.writeCharacteristic(it)
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

    fun usubscribeForUpdates() {
        getCharacteristic()?.let { characteristic ->
            handler.gattConnection?.setCharacteristicNotification(characteristic, true)
            val descriptor = characteristic.getDescriptor(configId)
            descriptor.value = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
            handler.gattConnection?.writeDescriptor(descriptor)
        }
    }

    private fun getCharacteristic(): BluetoothGattCharacteristic? = handler.gattService?.getCharacteristic(id)

    abstract val id: UUID
    abstract val configId: UUID

    abstract fun init(bluetoothGatt: BluetoothGatt)
    abstract fun onCharacteristicChanged(characteristic: BluetoothGattCharacteristic)
    abstract fun onCharacteristicRead(characteristic: BluetoothGattCharacteristic)
    abstract fun onCharacteristicWrite(characteristic: BluetoothGattCharacteristic)
}
