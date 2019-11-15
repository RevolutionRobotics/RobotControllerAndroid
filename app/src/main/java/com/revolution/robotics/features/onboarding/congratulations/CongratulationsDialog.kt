package com.revolution.robotics.features.onboarding.congratulations

import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogCongratulationsBinding
import com.revolution.robotics.features.mainmenu.MainMenuFragmentDirections
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class CongratulationsDialog : RoboticsDialog() {

    override val hasCloseButton: Boolean = true
    override val dialogFaces: List<DialogFace<*>> = listOf(CongratulationsDialogFace(this))
    override val dialogButtons: MutableList<DialogButton> = mutableListOf(
        DialogButton(R.string.onboarding_congratulations_button_label, R.drawable.ic_play, false) {
            navigator.navigate(MainMenuFragmentDirections.toChallangeList("ef504b31-d64f-4bfb-bd4b-5b96a9a0489f"))
            dismissAllowingStateLoss()
        }
    )

    class CongratulationsDialogFace(roboticsDialog: RoboticsDialog) :
        DialogFace<DialogCongratulationsBinding>(R.layout.dialog_congratulations, roboticsDialog)
}