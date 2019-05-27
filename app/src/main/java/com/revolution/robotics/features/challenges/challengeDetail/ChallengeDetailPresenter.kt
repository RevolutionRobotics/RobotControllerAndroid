package com.revolution.robotics.features.challenges.challengeDetail

import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeStep

class ChallengeDetailPresenter : ChallengeDetailMvp.Presenter, ChallengeDetailSlider.ChallengeStepSelectedListener {

    override var view: ChallengeDetailMvp.View? = null
    override var model: ChallengeDetailViewModel? = null

    override fun setChallenge(challenge: Challenge) {
        view?.initSlider(challenge.challengeSteps, this)
    }

    override fun shouldShowNext(challengeStep: ChallengeStep): Boolean = true

    override fun onChallengeStepSelected(challengeStep: ChallengeStep, fromUser: Boolean) {
        // Set test layout
    }

    override fun onChallengeFinished() {
        // TODO show finish modal
    }
}
