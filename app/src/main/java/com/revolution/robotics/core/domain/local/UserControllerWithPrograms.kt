package com.revolution.robotics.core.domain.local

data class UserControllerWithPrograms(
    var userController: UserController,
    var backgroundBindings: MutableList<UserBackgroundProgramBinding>,
    var programs: HashMap<String, UserProgram>
) {
    fun addBackgroundProgram(userProgram: UserProgram, priority: Int = 0) {
        backgroundBindings.add(
            UserBackgroundProgramBinding(
                0,
                userController.id,
                userProgram.name,
                priority
            )
        )
        programs?.put(userProgram.name, userProgram)
    }

    fun clearBackgroundPrograms() {
        backgroundBindings?.forEach {
            programs?.remove(it.programId)
        }
        backgroundBindings?.clear()
    }
}
