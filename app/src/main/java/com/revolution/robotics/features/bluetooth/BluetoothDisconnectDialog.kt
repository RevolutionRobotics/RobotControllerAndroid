package com.revolution.robotics.features.bluetooth

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogBluetoothDisconnectBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class BluetoothDisconnectDialog : RoboticsDialog() {
    override val hasCloseButton: Boolean = true
    override val dialogFaces: List<DialogFace<*>> = listOf(BluetoothDisconnectDialogFace(this))

    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(R.string.disconnect_modal_cancel, R.drawable.ic_close, false) {
            dismiss()
        },
        // TODO: Change icon
        DialogButton(R.string.disconnect_modal_disconnect, R.drawable.ic_close, true) {
            bluetoothManager.disconnect()
            dismiss()
        }
    )

    private val bluetoothManager: BluetoothManager by kodein.instance()

    class BluetoothDisconnectDialogFace(roboticsDialog: RoboticsDialog) :
        DialogFace<DialogBluetoothDisconnectBinding>(R.layout.dialog_bluetooth_disconnect, roboticsDialog) {

        override fun onActivated() {
            super.onActivated()
            (dialog as BluetoothDisconnectDialog).bluetoothManager.getDeviceInfoService().getSystemId({
                binding?.bluetoothDisconnectTitle?.text = it
            }, {})
        }
    }
}