package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerDao

class GetUserControllerForUserRobotInteractor(private val userControllerDao: UserControllerDao) :
    Interactor<UserController?>() {

    var robotId: Int = 0

    override fun getData(): UserController? = userControllerDao.getUserControllerForRobot(robotId)
}
