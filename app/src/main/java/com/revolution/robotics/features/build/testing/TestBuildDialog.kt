package com.revolution.robotics.features.build.testing

import android.os.Bundle
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.features.build.tips.DialogController
import com.revolution.robotics.features.build.tips.TipsDialogFace
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

class TestBuildDialog : RoboticsDialog(), DialogController {

    companion object {
        var Bundle.image: String by BundleArgumentDelegate.String("image")
        var Bundle.description: String by BundleArgumentDelegate.String("description")
        var Bundle.code: String by BundleArgumentDelegate.String("code")

        fun newInstance(image: String, description: String, code: String) =
            TestBuildDialog().withArguments { arguments ->
                arguments.image = image
                arguments.description = description
                arguments.code = code
            }
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(
        TestLoadingDialogFace(this),
        TestBuildDialogFace(this),
        TipsDialogFace(TestDialog.Source.BUILD, this, this)
    )

    override fun onCancelClicked() {
        dismissAllowingStateLoss()
    }

    override fun onRetryClicked() {
        activateFace(dialogFaces.first())
    }

    override fun publishDialogEvent(event: DialogEvent) {
        dialogEventBus.publish(event)
    }

    override fun showTips() {
        activateFace(dialogFaces[2])
    }

    override val hasCloseButton = true
    override val dialogButtons = emptyList<DialogButton>()
}
