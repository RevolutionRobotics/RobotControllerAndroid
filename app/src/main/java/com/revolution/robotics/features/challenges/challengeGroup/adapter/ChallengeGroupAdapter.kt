package com.revolution.robotics.features.challenges.challengeGroup.adapter

import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class ChallengeGroupAdapter : RecyclerAdapter<ChallengeGroupItem>() {

    init {
        adapterDelegate.register(
            ChallengeDelegateItem()
        )
    }
}
