package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.*
import com.revolution.robotics.core.extensions.isEmptyOrNull
import com.revolution.robotics.features.controllers.ControllerType
import java.util.*
import java.util.concurrent.TimeUnit

class GetTestRobotInteractor(
    private val userRobotDao: UserRobotDao,
    private val userConfigurationDao: UserConfigurationDao,
    private val userControllerDao: UserControllerDao
) : Interactor<UserRobot>() {

    override fun getData(): UserRobot {
        val robotList = userRobotDao.getAllRobots()
        return if (robotList.isEmptyOrNull()) {
            val configurationId = userConfigurationDao.saveUserConfiguration(UserConfiguration(0, null, UserMapping()))
            val userRobot = UserRobot(
                buildStatus = BuildStatus.COMPLETED,
                lastModified = Date(System.currentTimeMillis()),
                name = "Production test",
                configurationId = configurationId.toInt()
            )
            userRobot.instanceId = userRobotDao.saveUserRobot(userRobot).toInt()
            val controllerId = createController(userRobot.instanceId)
            userConfigurationDao.saveUserConfiguration(UserConfiguration(configurationId.toInt(), controllerId, UserMapping()))
            userRobot
        } else {
            robotList[0]
        }
    }

    private fun createController(robotId: Int): Int {
        val controller = UserController(
            robotId = robotId,
            type = ControllerType.GAMER.id,
            lastModified = System.currentTimeMillis() / TimeUnit.SECONDS.toMillis(1)
        )
        userControllerDao.saveUserController(controller).apply { return this.toInt() }
    }
}