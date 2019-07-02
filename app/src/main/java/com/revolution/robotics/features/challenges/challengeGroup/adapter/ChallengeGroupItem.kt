package com.revolution.robotics.features.challenges.challengeGroup.adapter

import com.revolution.robotics.core.domain.remote.ChallengeCategory
import com.revolution.robotics.features.challenges.challengeGroup.ChallengeGroupMvp
import kotlin.math.min

@Suppress("DataClassContainsFunctions")
data class ChallengeGroupItem(
    val iconUrl: String,
    val name: String,
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
    fun onItemClicked() {
        presenter.onItemClicked(challengeCategory)
    }
}
