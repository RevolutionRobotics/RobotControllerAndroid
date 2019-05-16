package com.revolution.robotics.core.domain.remote

data class ChallengeStep(
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var image: String? = null,
    var parts: List<Part> = emptyList()
)
