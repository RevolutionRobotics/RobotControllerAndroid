package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserRobotDao
import com.revolution.robotics.features.controllers.ControllerType

class GetControllerTypeInteractor(
    private val userRobotDao: UserRobotDao,
    private val controllerDao: UserControllerDao
) : Interactor<ControllerType>() {

    var robotId = -1

    override fun getData(): ControllerType {
        userRobotDao.getRobotById(robotId)?.configuration?.controller?.let { controllerId ->
            return ControllerType.fromId(controllerDao.getUserController(controllerId)?.type)
                ?: throw IllegalArgumentException("Invalid config")
        }
        throw IllegalArgumentException("Config missing")
    }
}
