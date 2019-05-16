package com.revolution.robotics.features.mainmenu.settings

import androidx.core.view.postDelayed
import com.revolution.robotics.R
import com.revolution.robotics.databinding.DialogResetTutorialBinding
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class TutorialResetDialogFace(dialog: RoboticsDialog) :
    DialogFace<DialogResetTutorialBinding>(R.layout.dialog_reset_tutorial, dialog) {

    companion object {
        private const val DELAY_DISMISS_MS = 2500L
    }

    override fun onActivated() {
        binding?.root?.postDelayed(DELAY_DISMISS_MS) {
            dialog?.dismissAllowingStateLoss()
        }
    }
}
