package com.revolution.robotics.features.build.turnOnTheBrain

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.openUrl
import com.revolution.robotics.databinding.DialogTurnOnTheBrainBinding
import com.revolution.robotics.features.build.tips.DialogController
import com.revolution.robotics.features.build.tips.TipsDialogFace
import com.revolution.robotics.features.community.CommunityFragment
import com.revolution.robotics.features.shared.ErrorHandler
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class TurnOnTheBrainDialog : RoboticsDialog(), DialogController {

    companion object {
        fun newInstance() = TurnOnTheBrainDialog()
    }

    private val errorHandler: ErrorHandler by kodein.instance()
    private val reporter: Reporter by kodein.instance()

    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(
        TurnOnTheBrainDialogFace(),
        TipsDialogFace(null, this, this)
    )
    override val dialogButtons = emptyList<DialogButton>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reporter.reportEvent(Reporter.Event.OPEN_BT_CONNECT_DIALOG)
    }
    override fun navigateToCommunity() {
        requireActivity().openUrl(CommunityFragment.COMMUNITY_FORUMS_URL, errorHandler)
    }

    override fun publishDialogEvent(event: DialogEvent) {
        dialogEventBus.publish(event)
    }

    override fun onCancelClicked() {
        onDialogCloseButtonClicked()
    }

    override fun onRetryClicked() {
        activateFace(dialogFaces.first { it is TurnOnTheBrainDialogFace })
    }

    override fun onDialogCloseButtonClicked(): Boolean {
        dialogEventBus.publish(DialogEvent.BRAIN_NOT_TURNED_ON)
        return true
    }

    inner class TurnOnTheBrainDialogFace : DialogFace<DialogTurnOnTheBrainBinding>(R.layout.dialog_turn_on_the_brain) {

        override val dialogFaceButtons = mutableListOf(
            DialogButton(R.string.build_robot_later, R.drawable.ic_clock) {
                dismissAllowingStateLoss()
                dialogEventBus.publish(DialogEvent.BRAIN_NOT_TURNED_ON)
            },
            DialogButton(R.string.build_robot_tips, R.drawable.ic_tips) {
                activateFace(dialogFaces.first { it is TipsDialogFace })
            },
            DialogButton(R.string.build_robot_start, R.drawable.ic_play, true) {
                val bluetoothManager = requireContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
                if (bluetoothManager.adapter.isEnabled) {
                    dismissAllowingStateLoss()
                    dialogEventBus.publish(DialogEvent.BRAIN_TURNED_ON)
                } else {
                    ContextCompat.startActivity(requireContext(), Intent().apply {
                        action = BluetoothAdapter.ACTION_REQUEST_ENABLE
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }, null)
                }
            }
        )
    }
}
