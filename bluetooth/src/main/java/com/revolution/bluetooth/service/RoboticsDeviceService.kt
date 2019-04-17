package com.revolution.bluetooth.service

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import com.revolution.bluetooth.exception.BLEConnectionException
import com.revolution.bluetooth.exception.BLEException
import java.util.UUID

class RoboticsDeviceService : RoboticsBLEService {

    companion object {
        val SERVICE_ID: UUID = UUID.fromString("F000180A-0451-4000-B000-000000000000")
        val SERIAL_NUMBER_CHARACTERISTIC: UUID = UUID.fromString("F0002A25-0451-4000-B000-000000000000")
        val MANUFACTURER_CHARACTERISTIC: UUID = UUID.fromString("F0002A29-0451-4000-B000-000000000000")
        val HW_REVISION_CHARACTERISTIC: UUID = UUID.fromString("F0002A27-0451-4000-B000-000000000000")
        val SW_REVISION_CHARACTERISTIC: UUID = UUID.fromString("F0002A28-0451-4000-B000-000000000000")
        val FW_REVISION_CHARACTERISTIC: UUID = UUID.fromString("F0002A26-0451-4000-B000-000000000000")
        val SYSTEM_ID_CHARACTERISTIC: UUID = UUID.fromString("F0002A23-0451-4000-B000-000000000000")
        val MODEL_NUMBER_CHARACTERISTIC: UUID = UUID.fromString("F0002A24-0451-4000-B000-000000000000")
    }

    private var service: BluetoothGattService? = null
    private var bluetoothGatt: BluetoothGatt? = null

    private val successCallbackMap = hashMapOf<UUID, (String) -> Unit>()
    private val errorCallbackMap = hashMapOf<UUID, (exception: BLEException) -> Unit>()

    override fun init(bluetoothGatt: BluetoothGatt) {
        this.bluetoothGatt = bluetoothGatt
        service = bluetoothGatt.getService(SERVICE_ID)
    }

    override fun disconnect() {
        successCallbackMap.clear()
        errorCallbackMap.clear()
        service = null
    }

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            successCallbackMap[characteristic.uuid]?.invoke(characteristic.getStringValue(0))
            errorCallbackMap.remove(characteristic.uuid)
        } else {
            errorCallbackMap[characteristic.uuid]?.invoke(BLEConnectionException(status))
            successCallbackMap.remove(characteristic.uuid)
        }
    }

    fun getSerialNumber(onCompleted: (String) -> Unit, onError: (exception: BLEException) -> Unit) {
        readCharachteristic(SERIAL_NUMBER_CHARACTERISTIC, onCompleted, onError)
    }

    fun getManufacturerName(onCompleted: (String) -> Unit, onError: (exception: BLEException) -> Unit) {
        readCharachteristic(MANUFACTURER_CHARACTERISTIC, onCompleted, onError)
    }

    fun getHardwareRevision(onCompleted: (String) -> Unit, onError: (exception: BLEException) -> Unit) {
        readCharachteristic(HW_REVISION_CHARACTERISTIC, onCompleted, onError)
    }

    fun getSoftwareRevision(onCompleted: (String) -> Unit, onError: (exception: BLEException) -> Unit) {
        readCharachteristic(SW_REVISION_CHARACTERISTIC, onCompleted, onError)
    }

    fun getFirmwareRevision(onCompleted: (String) -> Unit, onError: (exception: BLEException) -> Unit) {
        readCharachteristic(FW_REVISION_CHARACTERISTIC, onCompleted, onError)
    }

    fun getSystemId(onCompleted: (String) -> Unit, onError: (exception: BLEException) -> Unit) {
        readCharachteristic(SYSTEM_ID_CHARACTERISTIC, onCompleted, onError)
    }

    fun getModelNumber(onCompleted: (String) -> Unit, onError: (exception: BLEException) -> Unit) {
        readCharachteristic(MODEL_NUMBER_CHARACTERISTIC, onCompleted, onError)
    }

    override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) =
        Unit

    override fun onCharacteristicChanged(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic
    ) = Unit

    private fun readCharachteristic(
        uuid: UUID,
        onCompleted: (String) -> Unit,
        onError: (exception: BLEException) -> Unit
    ) {
        successCallbackMap[uuid] = onCompleted
        errorCallbackMap[uuid] = onError
        bluetoothGatt?.let { gatt ->
            service?.let { service ->
                gatt.readCharacteristic(service.getCharacteristic(uuid))
            }
        }
    }
}
