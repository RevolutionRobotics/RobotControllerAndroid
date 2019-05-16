package com.revolution.robotics.core.domain.remote

data class Challenge(
    var id: String? = null,
    var name: String? = null,
    var challengeSteps: List<ChallengeStep> = emptyList()
)
