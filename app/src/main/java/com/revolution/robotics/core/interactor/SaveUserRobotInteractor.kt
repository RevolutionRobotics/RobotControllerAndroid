package com.revolution.robotics.core.interactor

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

    override fun getData(): Long {
        val id = saveConfigurationDao.saveUserConfiguration(userConfiguration)
        userRobot.configurationId = id.toInt()
        return saveUserRobotDao.saveUserRobot(userRobot)
    }
}
