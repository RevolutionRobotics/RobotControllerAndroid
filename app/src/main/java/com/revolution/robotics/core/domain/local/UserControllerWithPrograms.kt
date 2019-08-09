package com.revolution.robotics.core.domain.local

import com.revolution.robotics.features.configure.controller.ControllerButton

data class UserControllerWithPrograms(
    var userController: UserController,
    var backgroundBindings: MutableList<UserBackgroundProgramBinding>,
    var programs: HashMap<String, UserProgram>,
    var programToBeAdded: UserProgram? = null
) {
    fun addButtonProgram(userProgram: UserProgram, buttonName: ControllerButton) {
        when (buttonName) {
            ControllerButton.B1 -> userController.mapping?.b1 =
                getNewProgramBinding(userProgram, userController.mapping?.b1)
            ControllerButton.B2 -> userController.mapping?.b2 =
                getNewProgramBinding(userProgram, userController.mapping?.b2)
            ControllerButton.B3 -> userController.mapping?.b3 =
                getNewProgramBinding(userProgram, userController.mapping?.b3)
            ControllerButton.B4 -> userController.mapping?.b4 =
                getNewProgramBinding(userProgram, userController.mapping?.b4)
            ControllerButton.B5 -> userController.mapping?.b5 =
                getNewProgramBinding(userProgram, userController.mapping?.b5)
            ControllerButton.B6 -> userController.mapping?.b6 =
                getNewProgramBinding(userProgram, userController.mapping?.b6)
        }
        programs[userProgram.name] = userProgram
    }

    fun removeButtonProgram(button: ControllerButton) {
        when (button) {
            ControllerButton.B1 -> {
                removeUserProgram(userController.mapping?.b1)
                userController.mapping?.b1 = null
            }
            ControllerButton.B2 -> {
                removeUserProgram(userController.mapping?.b2)
                userController.mapping?.b2 = null
            }
            ControllerButton.B3 -> {
                removeUserProgram(userController.mapping?.b3)
                userController.mapping?.b3 = null
            }
            ControllerButton.B4 -> {
                removeUserProgram(userController.mapping?.b4)
                userController.mapping?.b4 = null
            }
            ControllerButton.B5 -> {
                removeUserProgram(userController.mapping?.b5)
                userController.mapping?.b5 = null
            }
            ControllerButton.B6 -> {
                removeUserProgram(userController.mapping?.b6)
                userController.mapping?.b6 = null
            }
        }
    }

    private fun getNewProgramBinding(newProgram: UserProgram, currentBinding: UserProgramBinding?): UserProgramBinding {
        removeUserProgram(currentBinding)
        return UserProgramBinding(0, userController.id, newProgram.name, -1)
    }

    private fun removeUserProgram(currentBinding: UserProgramBinding?) {
        if (currentBinding != null) {
            programs.remove(currentBinding.programName)
        }
    }

}
