package com.revolution.robotics.features.build.buildFinished

import android.os.Bundle
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.databinding.DialogBuildFinishedBinding
import com.revolution.robotics.databinding.DialogFaceBuildRobotLoadingBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class BuildFinishedDialog : RoboticsDialog(), BuildFinishedMvp.View {

    companion object {
        fun newInstance() = BuildFinishedDialog()
    }

    private val presenter: BuildFinishedMvp.Presenter by kodein.instance()

    override val hasCloseButton = false
    override val dialogFaces: List<DialogFace<*>> = listOf(
        BuildFinishedLoadingDialogFace(this),
        BuildFinishedDialogFace(this)
    )
    override val dialogButtons: List<DialogButton> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, null)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun showBuildFinishedDialogFace() {
        activateFace(dialogFaces.first { it is BuildFinishedDialogFace })
    }

    override fun hideDialog() {
        dismissAllowingStateLoss()
    }

    class BuildFinishedLoadingDialogFace(dialog: RoboticsDialog) :
        DialogFace<DialogFaceBuildRobotLoadingBinding>(R.layout.dialog_face_build_robot_loading, dialog)

    class BuildFinishedDialogFace(dialog: RoboticsDialog) :
        DialogFace<DialogBuildFinishedBinding>(R.layout.dialog_build_finished, dialog) {
        override val dialogFaceButtons: MutableList<DialogButton> = mutableListOf(
            DialogButton(R.string.build_chapter_finish_dialog_button_home, R.drawable.ic_home) {
                dialog.dismissAllowingStateLoss()
                (dialog as? BuildFinishedDialog)?.presenter?.navigateHome()
            },
            DialogButton(R.string.build_robot_finished_button, R.drawable.ic_play, true) {
                dialog.dismissAllowingStateLoss()
                dialog.dialogEventBus.publish(DialogEvent.LETS_DRIVE)
            })
    }
}
