package com.revolution.robotics.features.configure

import com.revolution.robotics.core.domain.local.*
import com.revolution.robotics.core.interactor.SaveUserControllerInteractor
import com.revolution.robotics.core.interactor.UpdateUserRobotInteractor

@Suppress("TooManyFunctions")
class UserConfigurationStorage(
    private val updateUserRobotInteractor: UpdateUserRobotInteractor,
    private val saveUserControllerInteractor: SaveUserControllerInteractor
) {

    var robot: UserRobot? = null
    var userConfiguration: UserConfiguration? = null
    var controllerHolder: UserControllerWithPrograms? = null

    fun getBoundButtonPrograms() =
        getAllButtonPrograms().filterNotNull()

    fun getAllButtonPrograms() =
        listOf(
            controllerHolder?.userController?.mapping?.b1,
            controllerHolder?.userController?.mapping?.b2,
            controllerHolder?.userController?.mapping?.b3,
            controllerHolder?.userController?.mapping?.b4,
            controllerHolder?.userController?.mapping?.b5,
            controllerHolder?.userController?.mapping?.b6
        )

}
