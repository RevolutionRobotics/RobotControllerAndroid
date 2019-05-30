package com.revolution.robotics.features.build.testing

import android.net.Uri
import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogFaceFirmwareUpdateLoadingBinding
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance


class TestLoadingDialogFace(dialog: RoboticsDialog) :
    DialogFace<DialogFaceFirmwareUpdateLoadingBinding>(R.layout.dialog_face_firmware_update_loading, dialog) {

    private val bleManager: BluetoothManager by dialog.kodein.instance()

    override fun onActivated() {
        super.onActivated()

        bleManager.getConfigurationService()
            .testKit(Uri.parse("/data/data/com.revolution.robotics.dev/files/test.py"), {

            }, {

            })
    }
}