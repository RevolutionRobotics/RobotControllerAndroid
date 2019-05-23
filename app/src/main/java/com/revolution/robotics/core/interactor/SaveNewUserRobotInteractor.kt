package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserBackgroundProgramBinding
import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserButtonMapping
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserMapping
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramBinding
import com.revolution.robotics.core.domain.local.UserProgramDao
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.local.UserRobotDao
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.domain.remote.Controller
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.domain.remote.ProgramBinding

class SaveNewUserRobotInteractor(
    private val userRobotDao: UserRobotDao,
    private val userConfigurationDao: UserConfigurationDao,
    private val controllerDao: UserControllerDao,
    private val saveProgramDao: UserProgramDao,
    private val userBackgroundProgramBindingDao: UserBackgroundProgramBindingDao
) : Interactor<Long>() {

    lateinit var userRobot: UserRobot
    var configuration: Configuration? = null
    var controller: Controller? = null
    var programs: List<Program>? = null

    override fun getData(): Long {
        configuration?.let { remoteConfig ->
            val userConfiguration = createUserConfiguration(remoteConfig)
            val configurationId = userConfigurationDao.saveUserConfiguration(userConfiguration)
            userConfiguration.id = configurationId.toInt()
            userRobot.configurationId = configurationId.toInt()
            val userController = saveUserController(controller)
            userConfiguration.controller = userController.id
            userConfigurationDao.saveUserConfiguration(userConfiguration)
            val programIdMap = saveUserPrograms()

            userController.mapping?.let { userMapping ->
                controller?.mapping?.b1?.let {
                    userMapping.b1 = createUserButtonMapping(it, userController.id, programIdMap)
                }
                controller?.mapping?.b2?.let {
                    userMapping.b2 = createUserButtonMapping(it, userController.id, programIdMap)
                }
                controller?.mapping?.b3?.let {
                    userMapping.b3 = createUserButtonMapping(it, userController.id, programIdMap)
                }
                controller?.mapping?.b4?.let {
                    userMapping.b4 = createUserButtonMapping(it, userController.id, programIdMap)
                }
                controller?.mapping?.b5?.let {
                    userMapping.b5 = createUserButtonMapping(it, userController.id, programIdMap)
                }
                controller?.mapping?.b6?.let {
                    userMapping.b6 = createUserButtonMapping(it, userController.id, programIdMap)
                }
            }

            userBackgroundProgramBindingDao.saveBackgroundPrograms(
                controller?.backgroundProgramBindings?.map {
                    createBackgroundBinding(userController.id, it, programIdMap)
                } ?: emptyList()
            )
        }

        return userRobotDao.saveUserRobot(userRobot)
    }

    private fun createBackgroundBinding(
        controllerId: Int,
        binding: ProgramBinding,
        idMap: HashMap<String, Int>
    ): UserBackgroundProgramBinding =
        UserBackgroundProgramBinding(0, controllerId, idMap[binding.programId] ?: 0, binding.priority)

    private fun createUserButtonMapping(
        binding: ProgramBinding?,
        controllerId: Int,
        programIdMap: HashMap<String, Int>
    ): UserProgramBinding? {
        return binding?.let { remoteBinding ->
            UserProgramBinding(
                id = 0,
                controllerId = controllerId,
                programId = programIdMap[remoteBinding.programId] ?: 0,
                priority = remoteBinding.priority
            )
        }
    }

    private fun saveUserController(controller: Controller?): UserController {
        return controller?.let {
            val userController = createUserController(controller)
            val id = controllerDao.saveUserController(userController).toInt()
            userController.id = id
            return userController
        } ?: throw IllegalArgumentException("Missing controller")
    }

    private fun saveUserPrograms() = hashMapOf<String, Int>().apply {
        programs?.forEach { remoteProgram ->
            val currentProgram = remoteProgram.id?.let {
                saveProgramDao.getUserProgramBasedOnRemoteId(it)
            }

            this[remoteProgram.id ?: ""] = saveProgramDao.saveUserProgram(
                UserProgram(
                    currentProgram?.id ?: 0,
                    remoteProgram.description,
                    remoteProgram.lastModified,
                    remoteProgram.name,
                    remoteProgram.python,
                    remoteProgram.xml,
                    remoteProgram.variables,
                    remoteProgram.id
                )
            ).toInt()
        }
    }

    private fun createUserConfiguration(configuration: Configuration) =
        UserConfiguration(0, -1, UserMapping().apply {
            M1 = configuration.mapping?.M1
            M2 = configuration.mapping?.M2
            M3 = configuration.mapping?.M3
            M4 = configuration.mapping?.M4
            M5 = configuration.mapping?.M5
            M6 = configuration.mapping?.M6

            S1 = configuration.mapping?.S1
            S2 = configuration.mapping?.S2
            S3 = configuration.mapping?.S3
            S4 = configuration.mapping?.S4
        })

    private fun createUserController(controller: Controller) = UserController(
        id = 0,
        description = controller.description,
        lastModified = controller.lastModified,
        name = controller.name,
        type = controller.type,
        mapping = UserButtonMapping()
    )
}
