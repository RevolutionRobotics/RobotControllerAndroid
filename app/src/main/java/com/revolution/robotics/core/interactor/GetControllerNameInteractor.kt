package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserControllerDao

class GetControllerNameInteractor(
    private val configurationDao: UserConfigurationDao,
    private val controllerDao: UserControllerDao
) : Interactor<String?>() {

    var configurationId = -1

    override fun getData(): String? {
        configurationDao.getUserConfiguration(configurationId)?.controller?.let { controllerId ->
            return controllerDao.getUserController(controllerId)?.name
        }
        throw IllegalArgumentException("Config missing")
    }
}
