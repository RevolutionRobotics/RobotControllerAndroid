package com.revolution.robotics.core.domain.remote

data class Program(
    var description: String? = null,
    var id: String? = null,
    var lastModified: Long = 0,
    var name: String? = null,
    var python: String? = null,
    var xml: String? = null,
    var variables: List<String> = emptyList()
)
