package com.revolution.robotics.features.challenges.challengeDetail

import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeStep
import com.revolution.robotics.features.challenges.challengeDetail.adapter.ChallengePartItemViewModel

class ChallengeDetailPresenter : ChallengeDetailMvp.Presenter, ChallengeDetailSlider.ChallengeStepSelectedListener {

    override var view: ChallengeDetailMvp.View? = null
    override var model: ChallengeDetailViewModel? = null
    override var toolbarViewModel: ChallengeDetailToolbarViewModel? = null

    override fun setChallenge(challenge: Challenge) {
        view?.initSlider(challenge.challengeSteps, this)
        setChallengeStep(challenge.challengeSteps.first())
    }

    override fun shouldShowNext(challengeStep: ChallengeStep): Boolean = true

    override fun onChallengeStepSelected(challengeStep: ChallengeStep, fromUser: Boolean) {
        setChallengeStep(challengeStep)
    }

    private fun setChallengeStep(challengeStep: ChallengeStep) {
        toolbarViewModel?.title?.set(challengeStep.title)
        model?.apply {
            if (challengeStep.parts.isEmpty()) {
                image.value = challengeStep.image
                title.value = challengeStep.description
                isPartStep.value = false
                parts.value = emptyList()
            } else {
                image.value = null
                title.value = null
                isPartStep.value = true
                parts.value = challengeStep.parts.map {
                    ChallengePartItemViewModel(it)
                }
            }
        }
    }

    override fun onChallengeFinished() {
        // TODO show finish modal
    }
}
