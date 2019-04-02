package com.revolution.robotics.core.domain

data class BuildStep(
    var image: String? = null,
    var partImage: String? = null,
    var robotId: Int = 0,
    var stepNumber: Int = 0,
    var milestone: Milestone? = null
)