package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramDao
import com.revolution.robotics.core.domain.local.UserRobotDao

class SaveUserProgramInteractor(
    private val userProgramDao: UserProgramDao,
    private val controllerDao: UserControllerDao,
    private val backgroundProgramBindingDao: UserBackgroundProgramBindingDao,
    private val robotDao: UserRobotDao,
    private val configurationDao: UserConfigurationDao
) : Interactor<Unit>() {

    lateinit var userProgram: UserProgram
    var clearRemoteId = false

    override fun getData() {
        if (clearRemoteId) {
            userProgram.remoteId = null
        }
        userProgramDao.saveUserProgram(userProgram)
        controllerDao.getUserControllers().forEach { controller ->
            controller.getMappingList().find { it?.programName == userProgram.name }?.let {
                if (!isValidProgramForTheConfig(controller, userProgram)) {
                    controller.removeProgramMapping(userProgram.name)
                    controllerDao.updateUserController(controller)
                    return
                }
            }

            backgroundProgramBindingDao.getBackgroundPrograms(controller.id)
                .find { it.programId == userProgram.name }?.let {
                    if (!isValidProgramForTheConfig(controller, userProgram)) {
                        backgroundProgramBindingDao.removeBackgroundProgram(userProgram.name, controller.id)
                        return
                    }
                }
        }
    }

    private fun isValidProgramForTheConfig(userController: UserController, userProgram: UserProgram): Boolean {
        robotDao.getRobotById(userController.robotId)?.let { robot ->
            configurationDao.getUserConfiguration(robot.configurationId)?.let { config ->
                val configVariables = config.mappingId?.getVariables() ?: emptyList()
                return checkVariables(configVariables, userProgram)
            }
        }
        return false
    }

    private fun checkVariables(configVariables: List<String>, userProgram: UserProgram): Boolean {
        userProgram.variables.forEach { variable ->
            if (!configVariables.contains(variable)) {
                return false
            }
        }
        return true
    }
}
