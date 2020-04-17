package com.revolution.robotics.features.challenges.challengeGroup.adapter

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.core.domain.remote.LocalizedString
import com.revolution.robotics.features.challenges.challengeGroup.ChallengeGroupMvp
import kotlin.math.min

@Suppress("DataClassContainsFunctions")
data class ChallengeGroupItem(
    val iconUrl: String,
    val name: LocalizedString?,
    val downloaded: Boolean,
    private val currentChallenge: Int,
    private val totalChallenge: Int,
    private val challengeCategory: ChallengeCategory,
    private val presenter: ChallengeGroupMvp.Presenter
) {

    companion object {
        private const val ALMOST_100 = 0.99999f
    }

    fun getStepsText() = "$currentChallenge / $totalChallenge"
    fun getStepsProgress() = min(ALMOST_100, currentChallenge.toFloat() / totalChallenge)
    fun isComplete() = currentChallenge == totalChallenge

    val background = if (!downloaded) {
            R.drawable.bg_challenge_card_not_downloaded
        } else if (isComplete()) {
            R.drawable.bg_challenge_card_gold_selector
        } else {
            R.drawable.bg_challenge_card_grey_selector
        }

    fun onItemClicked() {
        presenter.onItemClicked(challengeCategory)
    }
}
