package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao

class GetUserRobotByConfigIdInteractor(private val userRobotDao: UserRobotDao) : Interactor<UserRobot?>() {

    var configId: Int = 0

    override fun getData() = userRobotDao.getRobotByConfigId(configId)
}