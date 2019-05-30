package com.revolution.robotics.features.build.testing

import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.features.build.tips.DialogController
import com.revolution.robotics.features.build.tips.TipsDialogFace
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.RoboticsDialog

abstract class TestDialog : RoboticsDialog(), DialogController {

    enum class Source {
        BUILD, CONFIGURE
    }

    override fun showTips() {
        activateFace(dialogFaces.first { it is TipsDialogFace })
    }

    override fun onCancelClicked() {
        dismissAllowingStateLoss()
    }

    override fun onRetryClicked() {
        activateFace(dialogFaces.first())
    }

    override fun publishDialogEvent(event: DialogEvent) {
        dialogEventBus.publish(event)
    }

    override val hasCloseButton = true
    override val dialogButtons = emptyList<DialogButton>()
}
