package com.revolution.robotics.features.configure.controller.program.priority

data class UserProgramBindingItem(
    var id: Int,
    var priority: Int,
    var type: ProgramType,
    var lastModified: Long,
    var name: String
)