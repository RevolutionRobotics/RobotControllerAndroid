package com.revolution.robotics.features.challenges.challengeList

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeCategory

interface ChallengeListMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, ChallengeListViewModel> {
        fun setChallengeCategory(challengeCategory: ChallengeCategory)
        fun onChallengeClicked(challengeStep: Challenge)
    }
}
