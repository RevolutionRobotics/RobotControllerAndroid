package com.revolution.robotics.core.domain

data class Robot(
    var id: Int = 0,
    var buildTime: String? = null,
    var configurationId: Int = 0,
    var coverImage: String? = null,
    var defaultProgram: String? = null,
    var description: String? = null,
    var name: String? = null
)
