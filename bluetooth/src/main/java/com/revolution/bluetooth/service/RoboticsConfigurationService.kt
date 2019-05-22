package com.revolution.bluetooth.service

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.net.Uri
import com.revolution.bluetooth.exception.BLEException
import com.revolution.bluetooth.exception.BLELongMessageIsAlreadyRunning
import com.revolution.bluetooth.exception.BLELongMessageValidationException
import java.util.UUID

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

        val CHARACHTERISTIC: UUID = UUID.fromString("d59bb321-7218-4fb9-abac-2f6814f31a4d")
    }

    override val serviceId: UUID = UUID.fromString(SERVICE_ID)

    var success: (() -> Unit)? = null
    var error: ((exception: BLEException) -> Unit)? = null
    var currentFile: Uri? = null

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
            bluetoothGatt?.writeCharacteristic(characteristic)
        }
    }

    private fun checkMd5(serverMd5: ByteArray, serverFileLength: Int) {
        // TODO Validate md5
        startUploading()
    }

    private fun readStatus(withDelay: Boolean) {

    }

    private fun startUploading() {

    }

    private fun sendNextChunk() {

    }

    private fun checkValidation() {

    }

    private fun isUploadInProgress() = currentFile != null

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) {
        when (characteristic.value[0]) {
            STATUS_UNUSED -> startUploading()
            STATUS_UPLOAD -> checkMd5(
                characteristic.value.copyOfRange(1, 17),
                characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT16, 17)
            )
            STATUS_VALIDATION -> readStatus(true)
            STATUS_READY -> {
                success?.invoke()
                success = null
                error = null
                currentFile = null
            }
            STATUS_VALIDATION_ERROR -> {
                error?.invoke(BLELongMessageValidationException())
                success = null
                error = null
                currentFile = null
            }
        }
    }

    override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) {
        when (characteristic.value[0]) {
            MESSAGE_TYPE_SELECT -> readStatus(false)
            MESSAGE_TYPE_INIT -> startUploading()
            MESSAGE_TYPE_UPLOAD -> sendNextChunk()
            MESSAGE_TYPE_FINALIZE -> checkValidation()
        }
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic) = Unit
}
