package com.revolution.bluetooth.discover

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import androidx.annotation.RequiresPermission
import com.revolution.bluetooth.exception.BLEScanFailedException
import com.revolution.bluetooth.extensions.getBLEManager

class RoboticsDeviceDiscoverer : ScanCallback() {

    var bluetoothAdapter: BluetoothAdapter? = null
    var scanResultListener: ScanResultListener? = null

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun discoverRobots(context: Context, scanResultListener: ScanResultListener) {
        bluetoothAdapter = context.getBLEManager().adapter
        this.scanResultListener = scanResultListener
        // TODO Add filters and settings
        bluetoothAdapter?.bluetoothLeScanner?.startScan(this)
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
        scanResultListener?.onScanResult(results)
    }

    override fun onScanResult(callbackType: Int, result: ScanResult) {
        scanResultListener?.onScanResult(listOf(result))
    }
}
