package com.revolution.robotics.core.domain.remote

data class BuildStep(
    var image: String? = null,
    var partImage: String? = null,
    var part2Image: String? = null,
    var robotId: Int = 0,
    var stepNumber: Int = 0,
    var milestone: Milestone? = null
)
