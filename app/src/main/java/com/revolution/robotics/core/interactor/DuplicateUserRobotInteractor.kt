package com.revolution.robotics.core.interactor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserBackgroundProgramBinding
import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.CameraHelper
import java.util.Date

class DuplicateUserRobotInteractor(
    private val userRobotDao: UserRobotDao,
    private val backgroundProgramBindingDao: UserBackgroundProgramBindingDao,
    private val controllerDao: UserControllerDao,
    private val userConfigurationDao: UserConfigurationDao,
    private val applicationContextProvider: ApplicationContextProvider,
    private val resourceResolver: ResourceResolver
) : Interactor<UserRobot>() {

    lateinit var currentRobot: UserRobot
    var selectedControllerId = -1

    override fun getData(): UserRobot {
        val robotCopy = currentRobot.copy()
        val currentConfigCopy = copyConfig()
        robotCopy.configurationId = currentConfigCopy?.id ?: 0
        robotCopy.lastModified = Date(System.currentTimeMillis())
        robotCopy.name = "${robotCopy.name} ${resourceResolver.string(R.string.duplicated_robot_name_suffix)}"

        robotCopy.instanceId = 0
        robotCopy.instanceId = userRobotDao.saveUserRobot(robotCopy).toInt()

        copyControllers(selectedControllerId, robotCopy.instanceId, currentConfigCopy)
        copyRobotImage(currentRobot.instanceId, robotCopy.instanceId)
        return robotCopy
    }

    private fun copyConfig(): UserConfiguration? {
        val currentConfigCopy = userConfigurationDao.getUserConfiguration(currentRobot.configurationId)?.copy()
        currentConfigCopy?.let { copyConfig ->
            selectedControllerId = copyConfig.controller ?: -1
            currentConfigCopy.id = 0
            currentConfigCopy.id = userConfigurationDao.saveUserConfiguration(currentConfigCopy).toInt()
        }
        return currentConfigCopy
    }

    private fun copyControllers(
        selectedControllerId: Int,
        newRobotId: Int,
        currentConfigCopy: UserConfiguration?
    ) {
        controllerDao.getUserControllersForRobot(currentRobot.instanceId).forEach { controller ->
            val controllerCopy = controller.copy()
            val saveAsDefault = controllerCopy.id == selectedControllerId
            controllerCopy.id = 0
            controllerCopy.robotId = newRobotId
            controllerCopy.id = controllerDao.saveUserController(controllerCopy).toInt()
            if (saveAsDefault) {
                currentConfigCopy?.let { copyConfig ->
                    copyConfig.controller = controllerCopy.id
                    userConfigurationDao.updateUserConfiguration(copyConfig)
                }
            }
            copyBackgroundPrograms(controller.id, controllerCopy.id)
        }
    }

    private fun copyBackgroundPrograms(oldControllerId: Int, newControllerId: Int) {
        val backgroundPrograms = mutableListOf<UserBackgroundProgramBinding>()
        backgroundProgramBindingDao.getBackgroundPrograms(oldControllerId).forEach {
            backgroundPrograms.add(it.copy(id = 0, controllerId = newControllerId))
        }
        backgroundProgramBindingDao.saveBackgroundPrograms(backgroundPrograms)
    }

    private fun copyRobotImage(oldRobotId: Int, newRobotId: Int) {
        val oldImage = CameraHelper.getImageFile(
            applicationContextProvider.applicationContext,
            CameraHelper.generateFilenameForRobot(oldRobotId, false)
        )
        if (oldImage.exists()) {
            oldImage.copyTo(
                CameraHelper.getImageFile(
                    applicationContextProvider.applicationContext,
                    CameraHelper.generateFilenameForRobot(newRobotId, false)
                )
            )
        }
    }
}
