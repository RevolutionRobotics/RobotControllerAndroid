package com.revolution.robotics.features.challenges.challengeDetail

import android.os.Bundle
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.DialogChallengeDetailFinishedBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

sealed class ChallengeDetailFinishedDialog(hasNextButton: Boolean) : RoboticsDialog() {

    companion object {
        protected var Bundle.challenge: Challenge by BundleArgumentDelegate.Parcelable("challenge")
        protected var Bundle.categoryId: String by BundleArgumentDelegate.String("categoryId")
    }

    private val navigator: Navigator by kodein.instance()

    override val hasCloseButton: Boolean = false
    override val dialogFaces: List<DialogFace<*>> = listOf(ChallengeDetailFinishedDialogFace(this))
    override val dialogButtons: MutableList<DialogButton> = mutableListOf(
        DialogButton(R.string.challenge_finished_dialog_button_home, R.drawable.ic_home, false) {
            dismiss()
            navigator.popUntil(R.id.mainMenuFragment)
        },
        DialogButton(R.string.challenge_finished_dialog_button_challenge_list, R.drawable.ic_cup, false) {
            dismiss()
            navigator.popUntil(R.id.challengeListFragment)
        }
    )

    init {
        if (hasNextButton) {
            dialogButtons.add(
                DialogButton(
                    R.string.challenge_finished_dialog_button_next,
                    R.drawable.ic_next_challenge,
                    true
                ) {
                    dismiss()
                    arguments?.let { arguments ->
                        navigator.navigate(
                            ChallengeDetailFragmentDirections.toNextChallenge(
                                arguments.challenge,
                                arguments.categoryId
                            )
                        )
                    }
                })
        }
    }

    class ChallengeDetailFinishedDialogFace(roboticsDialog: RoboticsDialog) :
        DialogFace<DialogChallengeDetailFinishedBinding>(R.layout.dialog_challenge_detail_finished, roboticsDialog)

    class Latest : ChallengeDetailFinishedDialog(false) {
        companion object {
            fun newInstance() = Latest()
        }
    }

    class Intermediate : ChallengeDetailFinishedDialog(true) {
        companion object {
            fun newInstance(challenge: Challenge, categoryId: String) =
                Intermediate().withArguments { bundle ->
                    bundle.challenge = challenge
                    bundle.categoryId = categoryId
                }
        }
    }
}