package com.revolution.robotics.features.bluetooth

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogConfirmBinding
import com.revolution.robotics.utils.ConfirmDialog
import org.kodein.di.erased.instance

class BluetoothDisconnectDialog : ConfirmDialog(R.string.disconnect_modal_disconnect) {

    companion object {
        fun newInstance() = BluetoothDisconnectDialog()
    }

    private val bluetoothManager: BluetoothManager by kodein.instance()

    override fun onConfirmed() {
        bluetoothManager.disconnect()
    }

    override fun onActivated(binding: DialogConfirmBinding?) {
        binding?.confirmDescription?.setText(R.string.disconnect_modal_description)
        bluetoothManager.getDeviceInfoService().getSystemId(
            onCompleted = { result ->
                binding?.confirmTitle?.text = result
            },
            onError = {}
        )
    }
}
