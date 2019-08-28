package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao

class UpdateUserRobotInteractor(
    private val saveConfigurationDao: UserConfigurationDao,
    private val saveUserRobotDao: UserRobotDao
) : Interactor<UserRobot>() {

    lateinit var userConfiguration: UserConfiguration
    lateinit var userRobot: UserRobot

    @Suppress("SwallowedException")
    override fun getData(): UserRobot {
        val configurationId = if (userConfiguration.id == 0) {
            saveConfigurationDao.saveUserConfiguration(userConfiguration)
        } else {
            saveConfigurationDao.updateUserConfiguration(userConfiguration)
            userConfiguration.id.toLong()
        }
        userRobot.configurationId = configurationId.toInt()

        val hasController = userConfiguration.controller != null && userConfiguration.controller != -1
        userRobot.buildStatus = if (hasController) BuildStatus.COMPLETED else BuildStatus.INVALID_CONFIGURATION

        saveUserRobotDao.updateUserRobot(userRobot)
        return userRobot
    }
}
