package com.revolution.robotics.core.domain.remote

import com.revolution.robotics.core.domain.PortMapping

data class Configuration(
    var controller: String? = null,
    var id: Int = 0,
    var mapping: PortMapping? = null
)
