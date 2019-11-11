package com.revolution.robotics.core.utils

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Configuration
import com.revolution.robotics.core.domain.remote.Controller
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.interactor.AssignConfigToRobotInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.UpdateUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.*

class CreateRobotInstanceHelper(
    private val robotInteractor: RobotInteractor,
    private val getProgramsForRobotInteractor: GetProgramsForRobotInteractor,
    private val configurationInteractor: ConfigurationInteractor,
    private val controllerInteractor: ControllerInteractor,
    private val assignConfigToRobotInteractor: AssignConfigToRobotInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val updateUserRobotInteractor: UpdateUserRobotInteractor
) {

    var userRobot: UserRobot? = null
    var onSuccess: ((userRobot: UserRobot, configuration: Configuration?, controller: Controller?) -> Unit)? =
        null
    var onError: ((throwable: Throwable) -> Unit)? = null
    var controller: Controller? = null
    var configuration: Configuration? = null

    fun setupConfigFromFirebase(
        userRobot: UserRobot,
        onSuccess: (userRobot: UserRobot, configuration: Configuration?, controllers: Controller?) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        this.userRobot = userRobot
        this.onSuccess = onSuccess
        this.onError = onError
        if (userRobot.remoteId != null) {
            saveUserRobotInteractor.userRobot = userRobot
            saveUserRobotInteractor.execute(onResponse = { savedRobot ->
                this.userRobot = savedRobot
                robotInteractor.robotId = userRobot.remoteId!!
                robotInteractor.execute { robot ->
                    configurationInteractor.configId = robot.configurationId ?: ""
                    configurationInteractor.execute { config ->
                        configuration = config
                        downloadControllerInfos(robot)
                    }
                }
            }, onError = onError)
        }
    }

    private fun downloadControllerInfos(robot: Robot) {
        controllerInteractor.configurationId = robot.configurationId ?: ""
        controllerInteractor.execute(onResponse = { controllers ->
            this.controller = controllers[0]
            downloadPrograms(robot)
        }, onError = onError)
    }

    private fun downloadPrograms(robot: Robot) {
        getProgramsForRobotInteractor.robot = robot
        getProgramsForRobotInteractor.execute(onResponse = { programs ->
            createLocalObjects(configuration, controller, programs)
        }, onError = onError)
    }

    private fun createLocalObjects(
        configuration: Configuration?,
        controller: Controller?,
        programs: List<Program>
    ) {
        userRobot?.let { userRobot ->
            updateUserRobotInteractor.userRobot = userRobot
            updateUserRobotInteractor.execute(onResponse = { savedRobot ->
                this.userRobot = savedRobot
                assignConfig(configuration, controller, programs)
            }, onError = onError)
        }
    }

    private fun assignConfig(
        configuration: Configuration?,
        controller: Controller?,
        programs: List<Program>
    ) {
        assignConfigToRobotInteractor.controller = controller
        assignConfigToRobotInteractor.configuration = configuration
        assignConfigToRobotInteractor.programs = programs
        userRobot?.let { userRobot ->
            assignConfigToRobotInteractor.userRobot = userRobot
            assignConfigToRobotInteractor.execute(
                onResponse = {
                    onSuccess?.invoke(it, configuration, controller)
                },
                onError = onError
            )
        }
    }
}