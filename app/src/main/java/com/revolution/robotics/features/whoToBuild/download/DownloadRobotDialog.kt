package com.revolution.robotics.features.whoToBuild.download

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.DialogFaceRobotDownloadingBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class DownloadRobotDialog : RoboticsDialog(), DownloadRobotMVP.View {

    companion object {
        private const val KEY_ROBOT_ID = "robot"

        private var Bundle.robotId by BundleArgumentDelegate.String(KEY_ROBOT_ID)

        fun newInstance(robotId: String): DownloadRobotDialog =
            DownloadRobotDialog().withArguments {
                it.robotId = robotId
            }
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(RobotDownloadingDialogFace(this))
    override val hasCloseButton = false
    override val dialogButtons: List<DialogButton> = listOf()

    private val presenter: DownloadRobotMVP.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        arguments?.robotId?.let { presenter.downloadRobot(it) }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun showError() {
        Toast.makeText(context, R.string.download_robot_error, Toast.LENGTH_LONG).show()
        dismiss()
    }

    override fun showSuccess() {
        dialogEventBus.publish(DialogEvent.ROBOT_DOWNLOADED)
        Toast.makeText(context, R.string.download_robot_success, Toast.LENGTH_LONG).show()
        dismiss()
    }

    class RobotDownloadingDialogFace(dialog: RoboticsDialog) :
        DialogFace<DialogFaceRobotDownloadingBinding>(
            R.layout.dialog_face_robot_downloading,
            dialog
        )
}