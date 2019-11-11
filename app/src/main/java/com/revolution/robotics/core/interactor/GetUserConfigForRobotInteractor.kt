package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserRobotDao

class GetUserConfigForRobotInteractor(
    private val userRobotDao: UserRobotDao
) : Interactor<UserConfiguration?>() {

    var robotId: Int = 0

    override fun getData(): UserConfiguration? {
        return userRobotDao.getRobotById(robotId)?.configuration
    }
}
