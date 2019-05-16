package com.revolution.robotics.core.domain.remote

data class ChallengeCategory(
    var id: String? = null,
    var name: String? = null,
    var image: String? = null,
    var description: String? = null,
    var challenges: List<Challenge> = emptyList()
)
