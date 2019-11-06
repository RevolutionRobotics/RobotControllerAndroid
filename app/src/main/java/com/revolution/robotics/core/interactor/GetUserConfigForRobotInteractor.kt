package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserRobotDao

class GetUserConfigForRobotInteractor(
    private val userRobotDao: UserRobotDao,
    private val userConfigurationDao: UserConfigurationDao
) : Interactor<UserConfiguration?>() {

    var robotId: Int = 0

    override fun getData(): UserConfiguration? {
        val robot = userRobotDao.getRobotById(robotId)
        return robot?.let { userConfigurationDao.getUserConfiguration(it.configurationId) }
    }
}
