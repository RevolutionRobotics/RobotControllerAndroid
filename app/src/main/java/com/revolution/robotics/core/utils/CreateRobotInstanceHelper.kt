package com.revolution.robotics.core.utils

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.domain.remote.Controller
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.interactor.AssignConfigToRobotInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.ConfigurationInteractor
import com.revolution.robotics.core.interactor.firebase.ControllerInteractor
import com.revolution.robotics.core.interactor.firebase.ProgramsInteractor
import com.revolution.robotics.core.interactor.firebase.RobotInteractor

class CreateRobotInstanceHelper(
    private val robotInteractor: RobotInteractor,
    private val configurationInteractor: ConfigurationInteractor,
    private val controllerInteractor: ControllerInteractor,
    private val programsInteractor: ProgramsInteractor,
    private val assignConfigToRobotInteractor: AssignConfigToRobotInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor
) {

    var userRobot: UserRobot? = null
    var onSuccess: ((userRobot: UserRobot, configuration: Configuration?, controllers: List<Controller>?) -> Unit)? = null
    var onError: ((throwable: Throwable) -> Unit)? = null
    var controllers: List<Controller>? = null
    var configuration: Configuration? = null

    fun setupConfigFromFirebase(userRobot: UserRobot,
                                onSuccess: (userRobot: UserRobot, configuration: Configuration?, controllers: List<Controller>?) -> Unit,
                                onError: (throwable: Throwable) -> Unit) {
        this.userRobot = userRobot
        this.onSuccess = onSuccess
        this.onError = onError
        if (userRobot.id != null) {
            robotInteractor.robotId = userRobot.id!!
            robotInteractor.execute { robot ->
                configurationInteractor.configId = robot.configurationId ?: ""
                configurationInteractor.execute { config ->
                    configuration = config
                    downloadControllerInfos(config.id ?: "")
                }
            }
        }
    }

    private fun downloadControllerInfos(configurationId: String) {
        controllerInteractor.configurationId = configurationId
        controllerInteractor.execute(onResponse = { controllers ->
            this.controllers = controllers
            val programIds = mutableListOf<String>()
            controllers.forEach { controller ->
                controller.getProgramIds().forEach { programId ->
                    if (!programIds.contains(programId)) {
                        programIds.add(programId)
                    }
                }
            }
            downloadPrograms(programIds)
        }, onError = onError)
    }

    private fun downloadPrograms(ids: List<String>) {
        programsInteractor.programIds = ids
        programsInteractor.execute(onResponse = { programs ->
            createLocalObjects(configuration, controllers, programs)
        }, onError = onError)
    }

    private fun createLocalObjects(
        configuration: Configuration?,
        controllers: List<Controller>?,
        programs: List<Program>
    ) {
        userRobot?.let { userRobot ->
            saveUserRobotInteractor.userRobot = userRobot
            saveUserRobotInteractor.execute(onResponse = { savedRobot ->
                this.userRobot = savedRobot
                assignConfig(configuration, controllers, programs)
            }, onError = onError)
        }
    }

    private fun assignConfig(
        configuration: Configuration?,
        controllers: List<Controller>?,
        programs: List<Program>
    ) {
        assignConfigToRobotInteractor.controllers = controllers
        assignConfigToRobotInteractor.configuration = configuration
        assignConfigToRobotInteractor.programs = programs
        userRobot?.let { userRobot ->
            assignConfigToRobotInteractor.userRobot = userRobot
            assignConfigToRobotInteractor.execute(
                onResponse = {
                    onSuccess?.invoke(it, configuration, controllers)
                },
                onError = onError
            )
        }
    }
}