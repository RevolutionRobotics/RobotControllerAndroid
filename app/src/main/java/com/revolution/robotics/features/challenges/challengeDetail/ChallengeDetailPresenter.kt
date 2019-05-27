package com.revolution.robotics.features.challenges.challengeDetail

import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeStep

class ChallengeDetailPresenter : ChallengeDetailMvp.Presenter, ChallengeDetailSlider.ChallengeStepSelectedListener {

    override var view: ChallengeDetailMvp.View? = null
    override var model: ChallengeDetailViewModel? = null
    override var toolbarViewModel: ChallengeDetailToolbarViewModel? = null

    override fun setChallenge(challenge: Challenge) {
        view?.initSlider(challenge.challengeSteps, this)
        toolbarViewModel?.title?.set(challenge.challengeSteps.first().title)
    }

    override fun shouldShowNext(challengeStep: ChallengeStep): Boolean = true

    override fun onChallengeStepSelected(challengeStep: ChallengeStep, fromUser: Boolean) {
        toolbarViewModel?.title?.set(challengeStep.title)
    }

    override fun onChallengeFinished() {
        // TODO show finish modal
    }
}
