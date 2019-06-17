package com.revolution.robotics.core.domain.local

data class UserControllerWithPrograms(
    var userController: UserController,
    var backgroundBindings: MutableList<UserBackgroundProgramBinding>,
    var programs: HashMap<String, UserProgram>,
    var programToBeAdded: UserProgram? = null
)
