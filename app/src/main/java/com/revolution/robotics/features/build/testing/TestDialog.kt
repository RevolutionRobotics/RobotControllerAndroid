package com.revolution.robotics.features.build.testing

import com.revolution.robotics.features.build.tips.DialogController
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog

abstract class TestDialog : RoboticsDialog(), DialogController {

    enum class Source {
        BUILD, CONFIGURE
    }

    override fun onCancelClicked() {
        dismissAllowingStateLoss()
    }

    override fun onRetryClicked() {
        activateFace(dialogFaces.first())
    }

    override val hasCloseButton = true
    override val dialogButtons = emptyList<DialogButton>()
}
