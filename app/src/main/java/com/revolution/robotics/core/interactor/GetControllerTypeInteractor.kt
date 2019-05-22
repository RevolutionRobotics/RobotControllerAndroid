package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.features.controllers.ControllerType

class GetControllerTypeInteractor(
    private val configurationDao: UserConfigurationDao,
    private val controllerDao: UserControllerDao
) : Interactor<ControllerType>() {

    var configurationId = -1

    override fun getData(): ControllerType {
        configurationDao.getUserConfiguration(configurationId)?.controller?.let { controllerId ->
            return ControllerType.fromId(controllerDao.getUserController(controllerId)?.type)
                ?: throw IllegalArgumentException("Invalid config")
        }
        throw IllegalArgumentException("Config missing")
    }
}