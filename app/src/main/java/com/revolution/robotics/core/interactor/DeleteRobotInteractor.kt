package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserRobotDao

class DeleteRobotInteractor(
    private val userRobotDao: UserRobotDao,
    private val userConfigurationDao: UserConfigurationDao
) : Interactor<Unit>() {

    var id = -1
    var configId = -1

    override fun getData() {
        userConfigurationDao.deleteConfiguration(configId)
        userRobotDao.deleteRobotById(id)
    }
}
