package com.revolution.bluetooth.characteristic

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.revolution.bluetooth.communication.BLEConnectionHandler
import com.revolution.bluetooth.communication.RoboticCharacteristicListener
import java.util.UUID

abstract class RoboticsCharacteristic(protected val handler: BLEConnectionHandler) {

    abstract val id: UUID
    abstract val configId: UUID

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

    abstract fun init(bluetoothGatt: BluetoothGatt)
    @WorkerThread
    abstract fun onCharacteristicChanged(characteristic: BluetoothGattCharacteristic)
    @WorkerThread
    abstract fun onCharacteristicRead(characteristic: BluetoothGattCharacteristic)
    @WorkerThread
    abstract fun onCharacteristicWrite(characteristic: BluetoothGattCharacteristic)

    @MainThread
    abstract fun invokeEvent(roboticCharacteristicListener: RoboticCharacteristicListener?)
}
