package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao

class UpdateUserRobotInteractor(
    private val saveUserRobotDao: UserRobotDao
) : Interactor<UserRobot>() {

    lateinit var userRobot: UserRobot

    @Suppress("SwallowedException")
    override fun getData(): UserRobot {


        val hasController = userRobot.configuration.controller != null && userRobot.configuration.controller != -1
        userRobot.buildStatus = if (hasController) BuildStatus.COMPLETED else BuildStatus.INVALID_CONFIGURATION

        saveUserRobotDao.updateUserRobot(userRobot)
        return userRobot
    }
}
