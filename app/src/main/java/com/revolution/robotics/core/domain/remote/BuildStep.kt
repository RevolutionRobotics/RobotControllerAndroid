package com.revolution.robotics.core.domain.remote

data class BuildStep(
    var id: String? = null,
    var image: String? = null,
    var stepNumber: Int = 0,
    var milestone: Milestone? = null
)
