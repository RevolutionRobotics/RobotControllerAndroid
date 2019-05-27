package com.revolution.robotics.features.challenges.challengeDetail

import com.revolution.robotics.core.Mvp

interface ChallengeDetailMvp : Mvp {

    interface View : Mvp.View {

    }

    interface Presenter : Mvp.Presenter<View, ChallengeDetailViewModel> {

    }

}
