package com.revolution.robotics.features.challenges.challengeDetail

import android.os.Bundle
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.blockly.utils.SoundHelper
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.DialogChallengeDetailFinishedBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog

sealed class ChallengeDetailFinishedDialog(hasNextButton: Boolean) : RoboticsDialog() {

    companion object {
        protected var Bundle.challenge: Challenge by BundleArgumentDelegate.Parcelable("challenge")
        protected var Bundle.categoryId: String by BundleArgumentDelegate.String("categoryId")
    }

    override val hasCloseButton: Boolean = false
    override val dialogFaces: List<DialogFace<*>> = listOf(ChallengeDetailFinishedDialogFace(this))
    override val dialogButtons: MutableList<DialogButton> = mutableListOf(
        DialogButton(R.string.challenge_finished_dialog_button_home, R.drawable.ic_home, false) {
            dismissAllowingStateLoss()
            navigator.popUntil(R.id.mainMenuFragment)
        },
        DialogButton(R.string.challenge_finished_dialog_button_challenge_list, R.drawable.ic_cup, false) {
            dismissAllowingStateLoss()
            navigator.popUntil(R.id.challengeListFragment)
        }
    )
    private val soundHelper = SoundHelper()

    init {
        if (hasNextButton) {
            dialogButtons.add(
                DialogButton(
                    R.string.challenge_finished_dialog_button_next,
                    R.drawable.ic_next_challenge,
                    true
                ) {
                    dismissAllowingStateLoss()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundHelper.playSound("sounds/ta_da.mp3", requireContext())
    }

    class ChallengeDetailFinishedDialogFace(roboticsDialog: RoboticsDialog) :
        DialogFace<DialogChallengeDetailFinishedBinding>(R.layout.dialog_challenge_detail_finished, roboticsDialog) {

        override fun onActivated() {
            super.onActivated()
            if (dialog is Latest) {
                binding?.txtChallengeFinished?.setText(R.string.challenge_finished_dialog_latest_title)
            }
        }
    }

    class Latest : ChallengeDetailFinishedDialog(false) {
        companion object {
            fun newInstance() = Latest()
        }

        init {
            dialogButtons[1].isHighlighted = true
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
