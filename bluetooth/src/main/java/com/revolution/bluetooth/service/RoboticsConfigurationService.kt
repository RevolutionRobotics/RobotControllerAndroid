package com.revolution.bluetooth.service

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.net.Uri
import com.revolution.bluetooth.exception.BLEException
import com.revolution.bluetooth.exception.BLELongMessageIsAlreadyRunning
import com.revolution.bluetooth.exception.BLELongMessageValidationException
import com.revolution.bluetooth.exception.BLESendingTimeoutException
import com.revolution.bluetooth.file.FileChunkHandler
import com.revolution.bluetooth.file.MD5Checker
import java.util.UUID

@Suppress("TooManyFunctions")
class RoboticsConfigurationService : RoboticsBLEService() {

    companion object {
        const val SERVICE_ID = "97148a03-5b9d-11e9-8647-d663bd873d93"

        const val FUNCTION_TYPE_FIRMWARE = 1.toByte()
        const val FUNCTION_TYPE_FRAMEWORK = 2.toByte()
        const val FUNCTION_TYPE_CONFIGURATION = 3.toByte()
        const val FUNCTION_TYPE_TESTKIT = 4.toByte()

        const val MESSAGE_TYPE_SELECT = 0.toByte()
        const val MESSAGE_TYPE_INIT = 1.toByte()
        const val MESSAGE_TYPE_UPLOAD = 2.toByte()
        const val MESSAGE_TYPE_FINALIZE = 3.toByte()

        const val STATUS_UNUSED = 0.toByte()
        const val STATUS_UPLOAD = 1.toByte()
        const val STATUS_VALIDATION = 2.toByte()
        const val STATUS_READY = 3.toByte()
        const val STATUS_VALIDATION_ERROR = 4.toByte()

        const val MAX_VALIDATION_COUNT = 30
        const val MD5_LENGTH = 16
        const val CHUNK_LENGTH = 20

        val CHARACTERISTIC: UUID = UUID.fromString("d59bb321-7218-4fb9-abac-2f6814f31a4d")
    }

    override val serviceId: UUID = UUID.fromString(SERVICE_ID)
    private val md5Checker = MD5Checker()
    private val fileChunkHandler = FileChunkHandler()

    var success: (() -> Unit)? = null
    var error: ((exception: BLEException) -> Unit)? = null
    var currentFile: Uri? = null
    var validationCounter = 0

    fun updateFirmware(file: Uri, onSuccess: () -> Unit, onError: (exception: BLEException) -> Unit) {
        initLongMessage(file, onSuccess, onError)
        startLongMessageFlow(FUNCTION_TYPE_FIRMWARE)
    }

    fun updateFramework(file: Uri, onSuccess: () -> Unit, onError: (exception: BLEException) -> Unit) {
        initLongMessage(file, onSuccess, onError)
        startLongMessageFlow(FUNCTION_TYPE_FRAMEWORK)
    }

    fun testKit(file: Uri, onSuccess: () -> Unit, onError: (exception: BLEException) -> Unit) {
        initLongMessage(file, onSuccess, onError)
        startLongMessageFlow(FUNCTION_TYPE_TESTKIT)
    }

    fun sendConfiguration(file: Uri, onSuccess: () -> Unit, onError: (exception: BLEException) -> Unit) {
        initLongMessage(file, onSuccess, onError)
        startLongMessageFlow(FUNCTION_TYPE_CONFIGURATION)
    }

    private fun startLongMessageFlow(typeId: Byte) {
        sendSelectLongMessage(typeId)
    }

    private fun initLongMessage(file: Uri, onSuccess: () -> Unit, onError: (exception: BLEException) -> Unit) {
        if (isUploadInProgress()) {
            onError.invoke(BLELongMessageIsAlreadyRunning())
        }
        currentFile = file
        success = onSuccess
        error = onError
    }

    private fun sendSelectLongMessage(typeId: Byte) {
        service?.getCharacteristic(RoboticsLiveControllerService.CHARACTERISTIC_ID)?.let { characteristic ->
            characteristic.value = ByteArray(2).apply {
                set(0, MESSAGE_TYPE_SELECT)
                set(1, typeId)
            }

            eventSerializer?.registerEvent {
                bluetoothGatt?.writeCharacteristic(characteristic) ?: false
            }
        }
    }

    private fun checkMd5(serverMd5: ByteArray) {
        currentFile?.let { uri ->
            val currentMD5 = MD5Checker().calculateMD5Hash(uri)
            if (currentMD5.contentEquals(serverMd5)) {
                sendFinalizeMessage()
            } else {
                startUploading(currentMD5)
            }
        }
    }

    private fun readStatus() {
        service?.getCharacteristic(CHARACTERISTIC)?.let { characteristic ->
            bluetoothGatt?.let { bluetoothGatt ->
                eventSerializer?.registerEvent {
                    bluetoothGatt.readCharacteristic(characteristic)
                }
            }
        }
    }

    private fun startUploading(fileMD5: ByteArray?) {
        currentFile?.let { currentFile ->
            val md5 = fileMD5 ?: md5Checker.calculateMD5Hash(currentFile)
            ByteArray(MD5_LENGTH + 1).apply {
                set(0, MESSAGE_TYPE_INIT)
                for (index in 1..MD5_LENGTH + 1) {
                    set(index, md5[index])
                }
                writeMessage(this)
            }
        }
    }

    private fun startChunkSending() {
        currentFile?.let {
            fileChunkHandler.init(it, CHUNK_LENGTH, MESSAGE_TYPE_UPLOAD)
        }
        sendNextChunk()
    }

    private fun sendNextChunk() {
        val nextChunk = fileChunkHandler.getNextChunk()
        if (nextChunk != null) {
            writeMessage(nextChunk)
        } else {
            sendFinalizeMessage()
        }
    }

    private fun sendFinalizeMessage() {
        ByteArray(1).apply {
            set(0, MESSAGE_TYPE_FINALIZE)
            writeMessage(this)
        }
    }

    private fun writeMessage(byteArray: ByteArray) {
        service?.getCharacteristic(RoboticsLiveControllerService.CHARACTERISTIC_ID)?.let { characteristic ->
            characteristic.value = byteArray
            eventSerializer?.registerEvent {
                bluetoothGatt?.writeCharacteristic(characteristic) ?: false
            }
        }
    }

    private fun isUploadInProgress() = currentFile != null

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) {
        when (characteristic.value[0]) {
            STATUS_UNUSED -> startUploading(null)
            STATUS_UPLOAD -> checkMd5(characteristic.value.copyOfRange(1, MD5_LENGTH + 1))
            STATUS_VALIDATION -> if (validationCounter < MAX_VALIDATION_COUNT) {
                readStatus()
                validationCounter++
            } else {
                error?.invoke(BLESendingTimeoutException())
                resetVariables()
            }
            STATUS_READY -> {
                success?.invoke()
                resetVariables()
            }
            STATUS_VALIDATION_ERROR -> {
                error?.invoke(BLELongMessageValidationException())
                resetVariables()
            }
        }
    }

    private fun resetVariables() {
        success = null
        error = null
        currentFile = null
        validationCounter = 0
    }

    override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) {
        when (characteristic.value[0]) {
            MESSAGE_TYPE_SELECT -> readStatus()
            MESSAGE_TYPE_INIT -> startChunkSending()
            MESSAGE_TYPE_UPLOAD -> sendNextChunk()
            MESSAGE_TYPE_FINALIZE -> readStatus()
        }
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic) = Unit
}
