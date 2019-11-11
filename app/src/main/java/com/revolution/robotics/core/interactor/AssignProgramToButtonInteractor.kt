package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.*
import com.revolution.robotics.features.configure.controller.ControllerButton

class AssignProgramToButtonInteractor(
    private val userRobotDao: UserRobotDao,
    private val userControllerDao: UserControllerDao
) : Interactor<UserController?>() {

    var robotId: Int = -1
    var button: ControllerButton? = null
    var userProgram: UserProgram? = null

    override fun getData(): UserController? {
        val robot = userRobotDao.getRobotById(robotId)
        val controller = robot?.configuration?.controller?.let { controllerId ->
            userControllerDao.getUserController(controllerId)
        }
        if (button != null) {
            if (userProgram != null) {
                controller?.addButtonProgram(userProgram!!, button!!)
            } else {
                controller?.removeButtonProgram(button!!)
            }
        }
        controller?.let { userControllerDao.updateUserController(it) }
        return controller
    }
}
