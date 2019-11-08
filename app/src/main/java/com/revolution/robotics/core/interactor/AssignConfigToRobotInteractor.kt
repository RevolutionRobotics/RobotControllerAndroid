package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.*
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.domain.remote.Controller
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.domain.remote.ProgramBinding
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class AssignConfigToRobotInteractor(
    private val userRobotDao: UserRobotDao,
    private val controllerDao: UserControllerDao,
    private val userProgramDao: UserProgramDao,
    private val userBackgroundProgramBindingDao: UserBackgroundProgramBindingDao,
    private val resourceResolver: ResourceResolver
) : Interactor<UserRobot>() {

    lateinit var userRobot: UserRobot
    var configuration: Configuration? = null
    var controller: Controller? = null
    var programs: List<Program>? = null

    override fun getData(): UserRobot {
        configuration?.let { remoteConfig ->
            userRobot.configuration = createUserConfiguration(remoteConfig)
            val programIdMap = saveUserPrograms()
            controller?.let { saveUserController(it, userRobot.configuration, true, programIdMap) }
            val hasController = userRobot.configuration.controller != null && userRobot.configuration.controller != -1
            userRobot.buildStatus = if (hasController) BuildStatus.COMPLETED else BuildStatus.INVALID_CONFIGURATION
            userRobotDao.updateUserRobot(userRobot)
        }

        return userRobot
    }

    private fun saveUserController(
        controller: Controller,
        userConfiguration: UserConfiguration,
        isDefault: Boolean,
        programIdMap: HashMap<String, String>
    ) {
        val userController = saveUserController(controller)
        if (isDefault) {
            userConfiguration.controller = userController.id
        }

        userController.mapping?.let { userMapping ->
            controller.mapping?.b1?.let {
                userMapping.b1 = createUserButtonMapping(it, userController.id, programIdMap)
            }
            controller.mapping?.b2?.let {
                userMapping.b2 = createUserButtonMapping(it, userController.id, programIdMap)
            }
            controller.mapping?.b3?.let {
                userMapping.b3 = createUserButtonMapping(it, userController.id, programIdMap)
            }
            controller.mapping?.b4?.let {
                userMapping.b4 = createUserButtonMapping(it, userController.id, programIdMap)
            }
            controller.mapping?.b5?.let {
                userMapping.b5 = createUserButtonMapping(it, userController.id, programIdMap)
            }
            controller.mapping?.b6?.let {
                userMapping.b6 = createUserButtonMapping(it, userController.id, programIdMap)
            }
        }

        userBackgroundProgramBindingDao.removeOldBackgroundBindings(userController.id)
        userBackgroundProgramBindingDao.saveBackgroundPrograms(
            controller.backgroundProgramBindings.map {
                createBackgroundBinding(userController.id, it, programIdMap)
            }
        )
        controllerDao.updateUserController(userController)
    }

    private fun createBackgroundBinding(
        controllerId: Int,
        binding: ProgramBinding,
        idMap: HashMap<String, String>
    ): UserBackgroundProgramBinding =
        UserBackgroundProgramBinding(0, controllerId, idMap[binding.programId] ?: "", binding.priority)

    private fun createUserButtonMapping(
        binding: ProgramBinding?,
        controllerId: Int,
        programIdMap: HashMap<String, String>
    ): UserProgramBinding? {
        return binding?.let { remoteBinding ->
            UserProgramBinding(
                id = 0,
                controllerId = controllerId,
                programName = programIdMap[remoteBinding.programId] ?: "",
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
        } ?: throw IllegalArgumentException("Missing controllers")
    }

    private fun saveUserPrograms() = hashMapOf<String, String>().apply {
        programs?.forEach { remoteProgram ->
            val newProgram = UserProgram(
                remoteProgram.description?.getLocalizedString(resourceResolver) ?: "",
                remoteProgram.lastModified,
                remoteProgram.name?.getLocalizedString(resourceResolver) ?: "",
                remoteProgram.python,
                remoteProgram.xml,
                userRobot.id,
                remoteProgram.variables,
                remoteProgram.id
            )
            this[remoteProgram.id ?: ""] = newProgram.name
            userProgramDao.saveUserProgram(newProgram)
        }
    }

    private fun createUserConfiguration(configuration: Configuration) =
        UserConfiguration(null, UserMapping().apply {
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
        robotId = userRobot.id,
        name = controller.name?.getLocalizedString(resourceResolver) ?: "",
        type = controller.type,
        description = controller.description?.getLocalizedString(resourceResolver) ?: "",
        lastModified = controller.lastModified,
        mapping = UserButtonMapping(),
        joystickPriority = controller.joystickPriority?.toInt() ?: 0
    )
}
