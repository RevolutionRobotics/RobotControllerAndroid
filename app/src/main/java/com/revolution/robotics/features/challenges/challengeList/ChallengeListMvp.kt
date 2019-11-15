package com.revolution.robotics.features.challenges.challengeList

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.core.domain.remote.ChallengeCategory

interface ChallengeListMvp : Mvp {

    interface View : Mvp.View {
        fun displayChallengeCategory(challengeCategory: ChallengeCategory)
    }

    interface Presenter : Mvp.Presenter<View, ChallengeListViewModel> {
        fun loadChallangeCategory(challengeCategoryId: String)
        fun onChallengeClicked(challengeStep: Challenge)
    }
}
