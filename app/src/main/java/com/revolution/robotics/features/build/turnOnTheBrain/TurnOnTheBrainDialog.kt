package com.revolution.robotics.features.build.turnOnTheBrain

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogTurnOnTheBrainBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class TurnOnTheBrainDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = TurnOnTheBrainDialog()
    }

    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(
        TurnOnTheBrainDialogFace()
    )

    override val dialogButtons = listOf(
        DialogButton(R.string.build_robot_later, R.drawable.ic_clock) {
            dialog.dismiss()
            dialogEventBus.publish(DialogEvent.BRAIN_NOT_TURNED_ON)
        },
        DialogButton(R.string.build_robot_tips, R.drawable.ic_tips) {
            // TODO show tips
        },
        DialogButton(R.string.build_robot_start, R.drawable.ic_play, true) {
            val bluetoothManager = requireContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            if (bluetoothManager.adapter.isEnabled) {
                dialog.dismiss()
                dialogEventBus.publish(DialogEvent.BRAIN_TURNED_ON)
            } else {
                ContextCompat.startActivity(requireContext(), Intent().apply {
                    action = Settings.ACTION_BLUETOOTH_SETTINGS
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }, null)
            }
        }
    )

    override fun onDialogCloseButtonClicked() {
        dialogEventBus.publish(DialogEvent.BRAIN_NOT_TURNED_ON)
    }

    class TurnOnTheBrainDialogFace : DialogFace<DialogTurnOnTheBrainBinding>(R.layout.dialog_turn_on_the_brain)
}
