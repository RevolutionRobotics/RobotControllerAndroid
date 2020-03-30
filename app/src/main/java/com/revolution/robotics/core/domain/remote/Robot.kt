package com.revolution.robotics.core.domain.remote

import com.revolution.robotics.core.domain.PortMapping

data class Robot(
    var id: String? = null,
    var name: LocalizedString? = null,
    var order: Int = 0,
    var description: LocalizedString? = null,
    var coverImage: String? = null,
    var controller: Controller? = null,
    var portMapping: PortMapping? = null,
    var buildTime: String? = null,
    var complexity: String? = null,
    var compatibility: List<String> = emptyList(),
    var buildSteps: List<BuildStep> = emptyList(),
    var programs: List<Program> = emptyList()
)
