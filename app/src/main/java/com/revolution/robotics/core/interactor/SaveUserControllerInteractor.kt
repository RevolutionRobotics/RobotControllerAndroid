package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserBackgroundProgramBinding
import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserRobotDao
import java.util.concurrent.TimeUnit

class SaveUserControllerInteractor(
    private val userControllerDao: UserControllerDao,
    private val userRobotDao: UserRobotDao,
    private val userConfigDao: UserConfigurationDao,
    private val userBackgroundProgramBindingDao: UserBackgroundProgramBindingDao
) : Interactor<UserController>() {

    lateinit var userController: UserController
    lateinit var backgroundProgramBindings: List<UserBackgroundProgramBinding>

    override fun getData(): UserController {
        userController.lastModified = System.currentTimeMillis() / TimeUnit.SECONDS.toMillis(1)
        if (userController.id == 0) {
            userControllerDao.saveUserController(userController).apply {
                userController.id = this.toInt()
            }
        } else {
            userControllerDao.updateUserController(userController)
        }

        userRobotDao.getRobotById(userController.robotId)?.let { robot ->
            userConfigDao.getUserConfiguration(robot.configurationId)?.let { config ->
                if (config.controller == null || config.controller == 0) {
                    config.controller = userController.id
                    userConfigDao.updateUserConfiguration(config)
                }
            }
        }

        backgroundProgramBindings.forEach {
            it.controllerId = userController.id
        }
        userBackgroundProgramBindingDao.removeOldBackgroundBindings(userController.id)
        userBackgroundProgramBindingDao.saveBackgroundPrograms(backgroundProgramBindings)
        return userController
    }
}
