package com.revolution.robotics.core.domain.remote

open class Program(
    var description: LocalizedString? = null,
    var id: String? = null,
    var lastModified: Long = 0,
    var name: LocalizedString? = null,
    var python: String? = null,
    var blocklyXml: String? = null,
    var variables: List<String> = emptyList()
)
