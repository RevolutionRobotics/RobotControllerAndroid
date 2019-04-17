package com.revolution.bluetooth.discover

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.ParcelUuid
import androidx.annotation.RequiresPermission
import com.revolution.bluetooth.communication.RoboticsDeviceConnector
import com.revolution.bluetooth.domain.Device
import com.revolution.bluetooth.exception.BLEScanFailedException
import com.revolution.bluetooth.extensions.getBLEManager

class RoboticsDeviceDiscoverer : ScanCallback() {

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var scanResultListener: ((List<Device>) -> Unit)? = null

    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH]
    )
    fun discoverRobots(context: Context, listener: (List<Device>) -> Unit) {
        bluetoothAdapter = context.getBLEManager().adapter
        scanResultListener = listener
        bluetoothAdapter?.bluetoothLeScanner?.startScan(
            listOf(
                ScanFilter.Builder().setServiceUuid(ParcelUuid(RoboticsDeviceConnector.SERVICE_ID_LIVE)).build(),
                ScanFilter.Builder().setServiceUuid(ParcelUuid(RoboticsDeviceConnector.SERVICE_ID_LONG)).build()
            ), ScanSettings.Builder().build(), this
        )
    }

    fun stopDiscovering() {
        bluetoothAdapter?.bluetoothLeScanner?.stopScan(this)
        bluetoothAdapter = null
        scanResultListener = null
    }

    override fun onScanFailed(errorCode: Int) {
        bluetoothAdapter?.bluetoothLeScanner?.stopScan(this)
        stopDiscovering()
        throw BLEScanFailedException(errorCode)
    }

    override fun onBatchScanResults(results: MutableList<ScanResult>) {
        scanResultListener?.invoke(results.map {
            Device(it.device.name, it.device.address, it.device)
        })
    }

    override fun onScanResult(callbackType: Int, result: ScanResult) {
        scanResultListener?.invoke(listOf(Device(result.device.name, result.device.address, result.device)))
    }
}
