package com.revolution.robotics.features.qr

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.zxing.Result
import com.revolution.robotics.core.interactor.PortTestFileCreatorInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.bluetooth.BluetoothConnectionListener
import com.revolution.robotics.features.bluetooth.BluetoothManager
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.erased.instance
import org.revolutionrobotics.robotcontroller.bluetooth.discover.RoboticsDeviceDiscoverer


class QrScannerFragment : Fragment(), ZXingScannerView.ResultHandler, BluetoothConnectionListener {


    companion object {
        private const val TEST_FOLDER = "testscripts"
    }

    private val ZXING_CAMERA_PERMISSION = 1

    private var mScannerView: ZXingScannerView? = null
    private val bleDeviceDiscoverer = RoboticsDeviceDiscoverer()

    private var kodein = LateInitKodein()

    private val navigator: Navigator by kodein.instance()
    private val bluetoothManager: BluetoothManager by kodein.instance()
    private val portTestFileCreatorInteractor: PortTestFileCreatorInteractor by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kodein.baseKodein = (requireContext().applicationContext as KodeinAware).kodein
    }

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
                arrayOf(
                    Manifest.permission.CAMERA
                ), ZXING_CAMERA_PERMISSION
            )
        } else {
            startScanning()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bluetoothManager.registerListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bluetoothManager.unregisterListener(this)
    }

    private fun startScanning() {
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
        bleDeviceDiscoverer.stopDiscovering()
    }

    override fun handleResult(rawResult: Result) {
        // Do something with the result here
        Log.v("Scanning", rawResult.text) // Prints scan results
        Log.v("Scanning", rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode, pdf417 etc.)
        if (bluetoothManager.isServiceDiscovered) {
            uploadTest()
        } else {
            bluetoothManager.startConnectionFlow(rawResult.text)
        }
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

    override fun onBluetoothConnectionStateChanged(connected: Boolean, serviceDiscovered: Boolean) {
        if (connected && serviceDiscovered) {
            uploadTest()
        }
    }

    private fun uploadTest() {
        portTestFileCreatorInteractor.assetFileName = "${QrScannerFragment.TEST_FOLDER}/productiontest.py"
        portTestFileCreatorInteractor.replaceablePairs = emptyList()
        portTestFileCreatorInteractor.execute(onResponse = {
            sendConfiguration(it)
        }, onError = {
            Log.e("TEST", it.localizedMessage)
            it.printStackTrace()
        })
    }

    private fun sendConfiguration(uri: Uri) {
        bluetoothManager.getConfigurationService().testKit(uri,
            onSuccess = {
                Log.e("TEST", "Test script sent")
                navigator.back()
            },
            onError = {
                Log.e("TEST", it.localizedMessage)
                navigator.back()
            })
    }
}