package com.revolution.robotics.features.bluetooth

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentActivity
import com.revolution.robotics.R
import com.revolution.robotics.core.bindings.setGreyscale
import org.kodein.di.KodeinAware
import org.kodein.di.erased.instance

class BluetoothStatusImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs), BluetoothConnectionListener {

    private val kodein = (context.applicationContext as KodeinAware).kodein
    private val bluetoothManager: BluetoothManager by kodein.instance()

    init {
        setBackgroundResource(R.drawable.bg_button_default)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        bluetoothManager.registerListener(this)
        setOnClickListener {
            if (!bluetoothManager.isConnected) {
                bluetoothManager.startConnectionFlow()
            } else {
                (context as? FragmentActivity)?.let {
                    BluetoothDisconnectDialog().show(it.supportFragmentManager)
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        bluetoothManager.unregisterListener(this)
        super.onDetachedFromWindow()
    }

    override fun onBluetoothConnectionStateChanged(connected: Boolean, serviceDiscovered: Boolean) {
        setGreyscale(this, !connected)
    }
}
