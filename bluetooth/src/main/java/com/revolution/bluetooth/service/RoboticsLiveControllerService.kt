package com.revolution.bluetooth.service

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import androidx.annotation.IntRange
import java.util.UUID
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.experimental.and
import kotlin.experimental.or

class RoboticsLiveControllerService : RoboticsBLEService {

    companion object {
        val CHARACHTERISTIC_ID = UUID.fromString("7486bec3-bb6b-4abd-a9ca-20adc281a0a4")
        val SERVICE_ID = UUID.fromString("d2d5558c-5b9d-11e9-8647-d663bd873d93")

        const val DELAY_TIME_IN_MILLIS = 100L
        const val COUNTER_MAX = 16

        const val POSITION_KEEP_ALIVE = 0
        const val POSITION_X_COORD = 1
        const val POSITION_Y_COORD = 2
        const val POSITION_BUTTON = 11
        const val MESSAGE_LENGTH = 20
        const val MAX_BYTE_MASK = 255.toByte()
    }

    private var service: BluetoothGattService? = null
    private var bluetoothGatt: BluetoothGatt? = null

    private var isRunning = false
    private var schedulerJob: Job? = null

    private var x = 0.toByte()
    private var y = 0.toByte()
    private var buttonByte = 0.toByte()

    override fun init(bluetoothGatt: BluetoothGatt) {
        this.bluetoothGatt = bluetoothGatt
        service = bluetoothGatt.getService(SERVICE_ID)
    }

    override fun disconnect() {
        bluetoothGatt = null
        service = null
    }

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
                    service?.getCharacteristic(CHARACHTERISTIC_ID)?.let { characteristic ->
                        characteristic.value = generateMessage(counter)
                        bluetoothGatt?.writeCharacteristic(characteristic)
                    }
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

    fun updateXDirection(@IntRange(from = 0, to = 255) x: Int) {
        this.x = x.toByte()
    }

    fun updateYDirection(@IntRange(from = 0, to = 255) y: Int) {
        this.y = y.toByte()
    }

    fun changeButtonState(buttonIndex: Int, pressed: Boolean) {
        buttonByte = if (pressed) {
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

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) =
        Unit

    override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) =
        Unit

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic) = Unit
}
