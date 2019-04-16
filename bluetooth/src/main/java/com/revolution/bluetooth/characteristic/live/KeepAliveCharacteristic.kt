package com.revolution.bluetooth.characteristic.live

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import com.revolution.bluetooth.communication.BLEConnectionHandler
import com.revolution.bluetooth.communication.RoboticCharacteristicListener
import java.util.UUID
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KeepAliveCharacteristic(handler: BLEConnectionHandler) : LiveRoboticsCharacteristic(handler) {

    companion object {
        const val DELAY_TIME_IN_MILLIS = 500L
        const val COUNTER_MAX = 16
    }

    override val id: UUID = UUID.fromString("9e55ea41-69c3-4729-9f9a-90bc27ab6253")
    override val configId: UUID? = null

    private var isRunning = false

    override fun init(bluetoothGatt: BluetoothGatt) = Unit

    override fun onCharacteristicChanged(characteristic: BluetoothGattCharacteristic) = Unit

    override fun onCharacteristicRead(characteristic: BluetoothGattCharacteristic) = Unit

    override fun onCharacteristicWrite(characteristic: BluetoothGattCharacteristic) {
        if (!isRunning) {
            return
        }

        GlobalScope.launch {
            delay(DELAY_TIME_IN_MILLIS)
            if (isRunning) {
                val currentValue = characteristic.value[0]
                write(ByteArray(1).apply {
                    this[0] = if (currentValue == COUNTER_MAX.toByte()) 0 else (currentValue + 1).toByte()
                })
            }
        }
    }

    override fun invokeEvent(roboticCharacteristicListener: RoboticCharacteristicListener?) = Unit

    fun start() {
        isRunning = true
        write(ByteArray(1).apply {
            this[0] = 0
        })
    }

    fun stop() {
        isRunning = false
    }
}
