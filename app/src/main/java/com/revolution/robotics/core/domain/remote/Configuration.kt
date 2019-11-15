package com.revolution.robotics.core.domain.remote

import com.revolution.robotics.core.domain.PortMapping

data class Configuration(
    var controller: String? = null,
    var id: String? = null,
    var mapping: PortMapping? = null
)
