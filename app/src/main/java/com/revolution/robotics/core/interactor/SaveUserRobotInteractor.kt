package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao

class SaveUserRobotInteractor(
    private val userRobotDao: UserRobotDao,
    private val userConfigurationDao: UserConfigurationDao
) : Interactor<Unit>() {

    lateinit var userRobot: UserRobot
    var userConfiguration: UserConfiguration? = null

    override fun getData() {
        userConfiguration?.let { userConfig ->
            val id = userConfigurationDao.saveUserConfiguration(userConfig)
            userRobot.configurationId = id.toInt()
        }
        userRobotDao.saveUserRobot(userRobot)
    }
}
