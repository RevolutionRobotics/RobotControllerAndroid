package com.revolution.robotics.features.qr

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.content.Intent
import android.Manifest.permission
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.widget.Toast


class QrScannerFragment : Fragment(), ZXingScannerView.ResultHandler {


    private val ZXING_CAMERA_PERMISSION = 1

    private var mScannerView: ZXingScannerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mScannerView = ZXingScannerView(context)
        return mScannerView
    }

    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA), ZXING_CAMERA_PERMISSION
            )
        } else {
            startScanning()
        }

        // Start camera on resume
    }

    private fun startScanning() {
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {
        // Do something with the result here
        Log.v("Scanning", rawResult.text) // Prints scan results
        Log.v("Scanning", rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        //mScannerView!!.resumeCameraPreview(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            ZXING_CAMERA_PERMISSION -> {
                if (grantResults.count() > 0 && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    startScanning()
                } else {
                    Toast.makeText(context, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT)
                        .show()
                }
                return
            }
        }
    }
}