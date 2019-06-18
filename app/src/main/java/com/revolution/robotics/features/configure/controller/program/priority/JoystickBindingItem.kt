package com.revolution.robotics.features.configure.controller.program.priority

data class JoystickBindingItem(
    override var priority: Int,
    override var lastModified: Long
) : BindingItem
