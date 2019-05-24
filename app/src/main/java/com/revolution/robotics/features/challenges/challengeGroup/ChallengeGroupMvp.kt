package com.revolution.robotics.features.challenges.challengeGroup

import com.revolution.robotics.core.Mvp

interface ChallengeGroupMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, ChallengeGroupViewModel>
}
