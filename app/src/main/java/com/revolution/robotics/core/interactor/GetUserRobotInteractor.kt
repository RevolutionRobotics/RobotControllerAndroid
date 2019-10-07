package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao

class GetUserRobotInteractor(private val userRobotDao: UserRobotDao) : Interactor<UserRobot?>() {

    var robotId: Int = 0

    override fun getData() = userRobotDao.getRobotById(robotId)
}
