package com.revolution.robotics.features.challenges.challengeList.adapter

import com.revolution.robotics.core.domain.remote.Challenge
import com.revolution.robotics.features.challenges.challengeList.ChallengeListMvp

class ChallengeListItem(
    val name: String,
    val position: String,
    val challenge: Challenge,
    val lineBackground: Int,
    val isLineVisible: Boolean,
    val isBottomItem: Boolean,
    val backgroundResource: Int,
    val textColor: Int,
    val indexTextColor: Int,
    val onClickEnabled: Boolean,
    val presenter: ChallengeListMvp.Presenter
) {

    fun onItemClicked() {
        presenter.onChallengeClicked(challenge)
    }
}
