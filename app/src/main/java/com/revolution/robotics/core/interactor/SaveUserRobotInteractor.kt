package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao

class SaveUserRobotInteractor(private val userRobotDao: UserRobotDao) : Interactor<Unit>() {

    lateinit var userRobot: UserRobot

    override fun getData() {
        userRobotDao.saveUserRobot(userRobot)
    }
}
