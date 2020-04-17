package com.revolution.robotics.features.challenges.challengeGroup.download

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.revolution.robotics.R
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.DialogFaceChallengeDownloadingBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class DownloadChallengeDialog : RoboticsDialog(), DownloadChallengeMVP.View {

    companion object {
        private const val KEY_CHALLENGE_CATEGORY_ID = "challenge"

        private var Bundle.challengeId by BundleArgumentDelegate.String(KEY_CHALLENGE_CATEGORY_ID)

        fun newInstance(challengeId: String): DownloadChallengeDialog =
            DownloadChallengeDialog().withArguments {
                it.challengeId = challengeId
            }
    }

    override val dialogFaces: List<DialogFace<*>> = listOf(ChallengeDownloadingDialogFace(this))
    override val hasCloseButton = false
    override val dialogButtons: List<DialogButton> = listOf()

    private val presenter: DownloadChallengeMVP.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        arguments?.challengeId?.let { presenter.downloadChallenge(it) }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun showError() {
        Toast.makeText(context, R.string.download_challenge_error, Toast.LENGTH_LONG).show()
        dismiss()
    }

    override fun showSuccess() {
        dialogEventBus.publish(DialogEvent.CHALLENGE_DOWNLOADED)
        Toast.makeText(context, R.string.download_challenge_success, Toast.LENGTH_LONG).show()
        dismiss()
    }

    class ChallengeDownloadingDialogFace(dialog: RoboticsDialog) :
        DialogFace<DialogFaceChallengeDownloadingBinding>(
            R.layout.dialog_face_challenge_downloading,
            dialog
        )
}