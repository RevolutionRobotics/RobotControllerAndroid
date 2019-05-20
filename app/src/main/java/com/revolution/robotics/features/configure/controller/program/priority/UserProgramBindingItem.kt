package com.revolution.robotics.features.configure.controller.program.priority

import com.revolution.robotics.core.domain.local.UserProgram

data class UserProgramBindingItem(
    var id: Int,
    var priority: Int,
    var type: ProgramType,
    var lastModified: Long,
    var name: String,
    var userProgram: UserProgram?
)
