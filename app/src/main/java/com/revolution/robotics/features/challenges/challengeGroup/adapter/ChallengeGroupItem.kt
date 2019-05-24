package com.revolution.robotics.features.challenges.challengeGroup.adapter

@Suppress("DataClassContainsFunctions")
data class ChallengeGroupItem(
    val iconUrl: String,
    val name: String,
    private val currentChallenge: Int,
    private val totalChallenge: Int
) {

    companion object {
        private const val ALMOST_100 = 0.99999f
    }

    fun getStepsText() = "$currentChallenge / $totalChallenge"
    fun getStepsProgress() = Math.min(ALMOST_100, currentChallenge.toFloat() / totalChallenge)
    fun isComplete() = currentChallenge == totalChallenge
}
