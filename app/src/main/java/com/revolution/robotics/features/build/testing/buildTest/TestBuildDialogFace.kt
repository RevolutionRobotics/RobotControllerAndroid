package com.revolution.robotics.features.build.testing.buildTest

import com.revolution.robotics.R
import com.revolution.robotics.core.bindings.loadFirebaseImage
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogTestingBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class TestBuildDialogFace(dialog: RoboticsDialog) :
    DialogFace<DialogTestingBinding>(R.layout.dialog_testing, dialog) {

    override val dialogFaceButtons = mutableListOf(
        DialogButton(R.string.testing_negative_button_title, R.drawable.ic_close,
            isHighlighted = false,
            isEnabledOnStart = true,
            onClick = ::showTipsFace
        ),
        DialogButton(R.string.testing_positive_button_title, R.drawable.ic_check,
            isHighlighted = true,
            isEnabledOnStart = true
        ) {
            dialog.dismissAllowingStateLoss()
            dialog.dialogEventBus.publish(DialogEvent.TEST_WORKS)
        }
    )

    override fun onActivated() {
        binding?.testingImage?.let { loadFirebaseImage(it, dialog?.arguments?.getString("image"), null, true) }
        binding?.testingText?.text = dialog?.arguments?.getString("description")
    }

    private fun showTipsFace() {
        (dialog as? TestBuildDialog)?.showTips()
    }
}
