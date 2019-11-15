package com.revolution.robotics.features.challenges.challengeDetail.adapter

import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class ChallengePartAdapter : RecyclerAdapter<ChallengePartItemViewModel>() {

    init {
        adapterDelegate.register(ChallengePartDelegateItem())
    }
}
