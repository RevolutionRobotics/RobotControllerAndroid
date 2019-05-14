package com.revolution.bluetooth.service

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import java.util.UUID

// TODO Implement long message protocol here
class RoboticsConfigurationService : RoboticsBLEService() {

    companion object {
        const val SERVICE_ID = "d2d5558c-5b9d-11e9-8647-d663bd873d93"

        val CHARACTERISTIC_BRAIN_TO_MOBILE: UUID = UUID.fromString("d59bb321-7218-4fb9-abac-2f6814f31a4d")
        val CHARACTERISTIC_MOBILE_TO_BRAIN: UUID = UUID.fromString("b81239d9-260b-4945-bcfe-8b1ef1fc2879")
    }

    override val serviceId: UUID = UUID.fromString(SERVICE_ID)

    /* Notify characteristic example
    eventSerializer?.registerEvent {
                    val uuid = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
                    val descriptor = characteristic.getDescriptor(uuid)
                    descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                    bluetoothGatt.writeDescriptor(descriptor)
                    bluetoothGatt.setCharacteristicNotification(characteristic, true)
                }
     */

    override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) =
        Unit

    override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic, status: Int) =
        Unit

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic) = Unit
}
