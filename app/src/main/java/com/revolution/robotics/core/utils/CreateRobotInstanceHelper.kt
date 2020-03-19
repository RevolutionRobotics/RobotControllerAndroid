package com.revolution.robotics.core.utils

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.core.interactor.AssignConfigToRobotInteractor
import com.revolution.robotics.core.interactor.SaveUserRobotInteractor
import com.revolution.robotics.core.interactor.UpdateUserRobotInteractor
import com.revolution.robotics.core.interactor.firebase.RobotInteractor

class CreateRobotInstanceHelper(
    private val robotInteractor: RobotInteractor,
    private val assignConfigToRobotInteractor: AssignConfigToRobotInteractor,
    private val saveUserRobotInteractor: SaveUserRobotInteractor,
    private val updateUserRobotInteractor: UpdateUserRobotInteractor
) {

    var userRobot: UserRobot? = null
    var onSuccess: ((userRobot: UserRobot) -> Unit)? =
        null
    var onError: ((throwable: Throwable) -> Unit)? = null

    fun setupConfigFromFirebase(
        userRobot: UserRobot,
        onSuccess: (userRobot: UserRobot) -> Unit,
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
                    robot?.let {
                        createLocalObjects(robot)
                    }
                }
            }, onError = onError)
        }
    }

    private fun createLocalObjects(robot: Robot) {
        userRobot?.let { userRobot ->
            updateUserRobotInteractor.userRobot = userRobot
            updateUserRobotInteractor.execute(onResponse = { savedRobot ->
                this.userRobot = savedRobot
                assignConfig(robot)
            }, onError = onError)
        }
    }

    private fun assignConfig(robot: Robot) {
        assignConfigToRobotInteractor.controller = robot.controller
        assignConfigToRobotInteractor.portMapping = robot.portMapping
        assignConfigToRobotInteractor.programs = robot.programs
        userRobot?.let { userRobot ->
            assignConfigToRobotInteractor.userRobot = userRobot
            assignConfigToRobotInteractor.execute(
                onResponse = {
                    onSuccess?.invoke(it)
                },
                onError = onError
            )
        }
    }
}