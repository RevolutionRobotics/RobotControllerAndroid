package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao

class GetAllUserRobotsInteractor(private val userRobotDao: UserRobotDao) : Interactor<List<UserRobot>>() {

    override fun getData(): List<UserRobot> =
        userRobotDao.getAllRobots()
}
