package com.revolution.robotics.core.domain.remote

import com.revolution.robotics.core.domain.ButtonMapping

data class Controller(
    var description: String? = null,
    var id: String? = null,
    var lastModified: Long = 0L,
    var name: String? = null,
    var type: String? = null,
    var backgroundProgramBindings: List<ProgramBinding> = emptyList(),
    var mapping: ButtonMapping? = null
) {
    companion object {
        const val TYPE_GAMER = "gamer"
        const val TYPE_MULTI_TASKER = "multiTasker"
        const val DRIVER = "driver"
    }
}
