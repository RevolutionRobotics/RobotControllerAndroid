package com.revolution.robotics.features.challenges.challengeDetail

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeStep

interface ChallengeDetailMvp : Mvp {

    interface View : Mvp.View {
        fun initSlider(steps: List<ChallengeStep>, listener: ChallengeDetailSlider.ChallengeStepSelectedListener)
        fun showChallengeFinishedDialog(nextChallenge: Challenge?)
        fun openUrl(url: String)
    }

    interface Presenter : Mvp.Presenter<View, ChallengeDetailViewModel> {
        var toolbarViewModel: ChallengeDetailToolbarViewModel?

        fun setChallenge(challenge: Challenge, categoryId: String?)
        fun onButtonClicked()
    }
}
