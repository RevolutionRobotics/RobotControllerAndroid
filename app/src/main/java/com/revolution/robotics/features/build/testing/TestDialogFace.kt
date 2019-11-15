package com.revolution.robotics.features.build.testing

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogTestingBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

abstract class TestDialogFace(dialog: RoboticsDialog) :
    DialogFace<DialogTestingBinding>(R.layout.dialog_testing, dialog) {

    abstract val imageResource: Int
    abstract val textResource: Int
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
        super.onActivated()
        binding?.testingImage?.setImageResource(imageResource)
        binding?.testingText?.setText(textResource)
    }

    abstract fun showTipsFace()
}
