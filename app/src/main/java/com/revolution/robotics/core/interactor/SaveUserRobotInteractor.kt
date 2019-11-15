package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao

class SaveUserRobotInteractor(private val userRobotDao: UserRobotDao) : Interactor<UserRobot>() {

    lateinit var userRobot: UserRobot

    override fun getData(): UserRobot {
        userRobot.id = userRobotDao.saveUserRobot(userRobot).toInt()
        return userRobot
    }
}
