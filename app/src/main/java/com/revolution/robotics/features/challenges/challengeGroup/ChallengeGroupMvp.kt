package com.revolution.robotics.features.challenges.challengeGroup

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.remote.ChallengeCategory

interface ChallengeGroupMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, ChallengeGroupViewModel> {
        fun onItemClicked(challenge: ChallengeCategory)
    }
}
