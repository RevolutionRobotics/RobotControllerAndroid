package com.revolution.robotics.core.domain

data class Configuration(
    var controller: String? = null,
    var id: Int = 0,
    var mapping: Mapping? = null
)
