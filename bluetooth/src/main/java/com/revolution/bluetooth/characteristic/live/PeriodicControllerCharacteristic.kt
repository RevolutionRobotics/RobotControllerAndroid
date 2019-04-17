package com.revolution.bluetooth.characteristic.live

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import androidx.annotation.IntRange
import com.revolution.bluetooth.communication.BLEConnectionHandler
import com.revolution.bluetooth.communication.RoboticCharacteristicListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.experimental.and
import kotlin.experimental.or

class PeriodicControllerCharacteristic(handler: BLEConnectionHandler) : LiveRoboticsCharacteristic(handler) {

    companion object {
        const val DELAY_TIME_IN_MILLIS = 500L
        const val COUNTER_MAX = 16

        const val POSITION_KEEP_ALIVE = 0
        const val POSITION_X_COORD = 1
        const val POSITION_Y_COORD = 2
        const val POSITION_BUTTON = 11
        const val MESSAGE_LENGTH = 20
        const val MAX_BYTE_MASK = 255.toByte()
    }

    override val id: UUID = UUID.fromString("7486bec3-bb6b-4abd-a9ca-20adc281a0a4")
    override val configId: UUID? = null

    private var isRunning = false
    private var schedulerJob: Job? = null

    private var x = 0.toByte()
    private var y = 0.toByte()
    private var buttonByte = 0.toByte()

    override fun init(bluetoothGatt: BluetoothGatt) = Unit

    override fun onCharacteristicChanged(characteristic: BluetoothGattCharacteristic) = Unit

    override fun onCharacteristicRead(characteristic: BluetoothGattCharacteristic) = Unit

    override fun onCharacteristicWrite(characteristic: BluetoothGattCharacteristic) = Unit

    override fun invokeEvent(roboticCharacteristicListener: RoboticCharacteristicListener?) = Unit

    fun start() {
        isRunning = true
        var counter = 0
        schedulerJob = GlobalScope.launch {
            do {
                if (isRunning) {
                    counter++
                    if (counter == COUNTER_MAX) {
                        counter = 0
                    }
                    write(generateMessage(counter))
                }
                delay(DELAY_TIME_IN_MILLIS)
            } while (isRunning)
        }
    }

    fun stop() {
        isRunning = false
        schedulerJob?.cancel()
        schedulerJob = null
    }

    fun updateDirection(@IntRange(from = 0, to = 255) x: Int, @IntRange(from = 0, to = 255) y: Int) {
        this.x = x.toByte()
        this.y = y.toByte()
    }

    fun changeButtonState(buttonIndex: Int, enable: Boolean) {
        buttonByte = if (enable) {
            buttonByte or getMaskBasedOnIndex(buttonIndex)
        } else {
            buttonByte and (MAX_BYTE_MASK - getMaskBasedOnIndex(buttonIndex)).toByte()
        }
    }

    private fun getMaskBasedOnIndex(buttonIndex: Int) = (2 pow buttonIndex).toByte()

    private fun generateMessage(counter: Int) = ByteArray(MESSAGE_LENGTH).apply {
        this[POSITION_KEEP_ALIVE] = counter.toByte()
        this[POSITION_X_COORD] = x
        this[POSITION_Y_COORD] = y
        this[POSITION_BUTTON] = buttonByte
    }


    private infix fun Int.pow(exponent: Int): Int {
        var res = 1
        for (i in exponent downTo 1) {
            res *= this
        }
        return res
    }
}
