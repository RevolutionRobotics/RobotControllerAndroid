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

        // TODO remove this when we introduce new BuildStatus (completed, but not playable)
        if (userRobot.isCustomBuild()) {
            val hasAssignedPort = !userConfiguration.mappingId?.getVariables()?.firstOrNull().isNullOrEmpty()
            val hasController = userConfiguration.controller != null && userConfiguration.controller != 0
            userRobot.buildStatus =
                if (hasAssignedPort && hasController) {
                    BuildStatus.COMPLETED
                } else {
                    BuildStatus.IN_PROGRESS
                }
        }

        saveUserRobotDao.updateUserRobot(userRobot)
        return userRobot
    }
}
