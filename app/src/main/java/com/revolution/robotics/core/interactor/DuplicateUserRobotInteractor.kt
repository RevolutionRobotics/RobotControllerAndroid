package com.revolution.robotics.core.interactor

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.*
import com.revolution.robotics.core.kodein.utils.ApplicationContextProvider
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.CameraHelper
import java.util.Date

class DuplicateUserRobotInteractor(
    private val userRobotDao: UserRobotDao,
    private val backgroundProgramBindingDao: UserBackgroundProgramBindingDao,
    private val userProgramDao: UserProgramDao,
    private val controllerDao: UserControllerDao,
    private val applicationContextProvider: ApplicationContextProvider,
    private val resourceResolver: ResourceResolver
) : Interactor<UserRobot>() {

    lateinit var currentRobot: UserRobot
    private var selectedControllerId = -1


    override fun getData(): UserRobot {
        val robotCopy = currentRobot.copy()

        val currentConfigCopy = copyConfig()
        currentConfigCopy?.let { robotCopy.configuration = it }
        robotCopy.lastModified = Date(System.currentTimeMillis())
        robotCopy.name = "${robotCopy.name} ${resourceResolver.string(R.string.duplicated_robot_name_suffix)}"

        robotCopy.id = 0
        robotCopy.id = userRobotDao.saveUserRobot(robotCopy).toInt()

        copyPrograms(robotCopy.id)

        copyController(robotCopy.id, currentConfigCopy)
        userRobotDao.updateUserRobot(robotCopy)
        copyRobotImage(currentRobot.id, robotCopy.id)
        return robotCopy
    }

    private fun copyConfig(): UserConfiguration? {
        val currentConfigCopy = currentRobot.configuration.copy()
        selectedControllerId = currentConfigCopy.controller ?: -1
        return currentConfigCopy
    }

    private fun copyPrograms(newRobotId: Int) {
        val programsToDuplicate = userProgramDao.getUserProgramsForRobot(currentRobot.id)
        programsToDuplicate.forEach { program ->
            val newProgram = program.copy(robotId = newRobotId)
            userProgramDao.saveUserProgram(newProgram)
        }
    }

    private fun copyController(
        newRobotId: Int,
        currentConfigCopy: UserConfiguration?
    ) {
        controllerDao.getUserControllerForRobot(currentRobot.id)?.apply {
            val controllerCopy = copy()
            controllerCopy.id = 0
            controllerCopy.robotId = newRobotId
            controllerCopy.id = controllerDao.saveUserController(controllerCopy).toInt()
            currentConfigCopy?.let { copyConfig -> copyConfig.controller = controllerCopy.id }
            copyBackgroundPrograms(id, controllerCopy.id)
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
        val oldImage = getImageForRobot(oldRobotId)
        if (oldImage.exists()) {
            oldImage.copyTo(getImageForRobot(newRobotId))
        }
    }

    private fun getImageForRobot(robotId: Int) =
        CameraHelper.getImageFile(
            applicationContextProvider.applicationContext,
            CameraHelper.generateFilenameForRobot(robotId)
        )
}
