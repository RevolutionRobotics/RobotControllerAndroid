package com.revolution.robotics.core.interactor

import android.database.sqlite.SQLiteConstraintException
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao

class SaveUserRobotInteractor(
    private val saveConfigurationDao: UserConfigurationDao,
    private val saveUserRobotDao: UserRobotDao
) : Interactor<Long>() {

    lateinit var userConfiguration: UserConfiguration
    lateinit var userRobot: UserRobot

    @Suppress("SwallowedException")
    override fun getData(): Long {
        val configurationId = saveConfigurationDao.saveUserConfiguration(userConfiguration)
        userRobot.configurationId = configurationId.toInt()
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

        return try {
            saveUserRobotDao.saveUserRobot(userRobot)
        } catch (exception: SQLiteConstraintException) {
            saveUserRobotDao.updateUserRobot(userRobot)
            userRobot.instanceId.toLong()
        }
    }
}
