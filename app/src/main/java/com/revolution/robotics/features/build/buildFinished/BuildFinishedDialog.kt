package com.revolution.robotics.features.build.buildFinished

import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogBuildFinishedBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class BuildFinishedDialog : RoboticsDialog() {

    companion object {
        fun newInstance() = BuildFinishedDialog()
    }

    private val presenter: BuildFinishedMvp.Presenter by kodein.instance()

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        BuildFinishedDialogFace()
    )
    override val dialogButtons = listOf(
        DialogButton(R.string.build_chapter_finish_dialog_button_home, R.drawable.ic_home) {
            dismissAllowingStateLoss()
            presenter.navigateHome()
        },
        DialogButton(R.string.build_robot_finished_button, R.drawable.ic_play, true) {
            dismissAllowingStateLoss()
            dialogEventBus.publish(DialogEvent.LETS_DRIVE)
        }
    )

    class BuildFinishedDialogFace : DialogFace<DialogBuildFinishedBinding>(R.layout.dialog_build_finished)
}
