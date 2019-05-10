package com.revolution.robotics.features.challenges.challengeList.adapter

import com.revolution.robotics.core.utils.recyclerview.RecyclerAdapter

class ChallengeListAdapter : RecyclerAdapter<ChallengeItem>() {

    init {
        adapterDelegate.register(
            ChallengeDelegateItem()
        )
    }
}
