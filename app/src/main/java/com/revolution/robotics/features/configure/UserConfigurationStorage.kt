package com.revolution.robotics.features.configure

import com.revolution.robotics.core.domain.local.*
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.core.interactor.SaveUserControllerInteractor
import com.revolution.robotics.core.interactor.UpdateUserRobotInteractor

@Suppress("TooManyFunctions")
class UserConfigurationStorage(
    private val updateUserRobotInteractor: UpdateUserRobotInteractor,
    private val saveUserControllerInteractor: SaveUserControllerInteractor
) {

    companion object {
        const val ALLOWED_DIGITS_REGEXP = "[a-zA-Z0-9]+"
    }

    var robot: UserRobot? = null
    var userConfiguration: UserConfiguration? = null
    var controllerHolder: UserControllerWithPrograms? = null
    var programBeingEdited: UserProgram? = null

    fun isUsedVariableName(name: String, portName: String): Boolean = collectVariableNames(portName).contains(name)

    fun updateRobot(finished: (() -> Unit)? = null) {
        userConfiguration?.let { config ->
            robot?.let { robot ->
                updateUserRobotInteractor.userConfiguration = config
                updateUserRobotInteractor.userRobot = robot
                updateUserRobotInteractor.execute(onResponse = {
                    finished?.invoke()
                }, onError = { finished?.invoke() })
            }
        }
    }

    fun getDefaultUltrasonicName(): String {
        var count = 0
        userConfiguration?.mappingId?.let { mapping ->
            if (mapping.S1?.type == Sensor.TYPE_ULTRASONIC) count++
            if (mapping.S2?.type == Sensor.TYPE_ULTRASONIC) count++
            if (mapping.S3?.type == Sensor.TYPE_ULTRASONIC) count++
            if (mapping.S4?.type == Sensor.TYPE_ULTRASONIC) count++
        }
        return if (count == 0) {
            "distance"
        } else {
            "distance${count + 1}"
        }
    }

    fun getDefaultBumperName(): String {
        var count = 0
        userConfiguration?.mappingId?.let { mapping ->
            if (mapping.S1?.type == Sensor.TYPE_BUMPER) count++
            if (mapping.S2?.type == Sensor.TYPE_BUMPER) count++
            if (mapping.S3?.type == Sensor.TYPE_BUMPER) count++
            if (mapping.S4?.type == Sensor.TYPE_BUMPER) count++
        }
        return if (count == 0) {
            "bumper"
        } else {
            "bumper${count + 1}"
        }
    }

    fun getPriority(userProgramName: String) =
        getAllButtonPrograms().find { it?.programName == userProgramName }?.priority
            ?: controllerHolder?.backgroundBindings?.find { it.programId == userProgramName }?.priority ?: -1

    fun getBoundButtonPrograms() =
        getAllButtonPrograms().filterNotNull()

    fun getAllButtonPrograms() =
        listOf(
            controllerHolder?.userController?.mapping?.b1,
            controllerHolder?.userController?.mapping?.b2,
            controllerHolder?.userController?.mapping?.b3,
            controllerHolder?.userController?.mapping?.b4,
            controllerHolder?.userController?.mapping?.b5,
            controllerHolder?.userController?.mapping?.b6
        )

    fun addBackgroundProgram(userProgram: UserProgram, priority: Int = 0) {
        controllerHolder?.backgroundBindings?.add(
            UserBackgroundProgramBinding(
                0,
                controllerHolder?.userController?.id ?: 0,
                userProgram.name,
                priority
            )
        )
        controllerHolder?.programs?.put(userProgram.name, userProgram)
        updateUserController()
    }

    fun clearBackgroundPrograms() {
        controllerHolder?.backgroundBindings?.forEach {
            controllerHolder?.programs?.remove(it.programId)
        }
        controllerHolder?.backgroundBindings?.clear()
    }

    private fun updateUserController(finished: (() -> Unit)? = null) {
        controllerHolder?.userController?.let { userController ->
            saveUserControllerInteractor.userController = userController
            saveUserControllerInteractor.backgroundProgramBindings = controllerHolder?.backgroundBindings ?: emptyList()
            userController.robotId = robot?.instanceId ?: 0
            saveUserControllerInteractor.execute(onResponse = { controller ->
                if (userConfiguration?.controller == null || userConfiguration?.controller == -1) {
                    userConfiguration?.controller = controller.id
                    val hasAssignedPort = !userConfiguration?.mappingId?.getVariables()?.firstOrNull().isNullOrEmpty()
                    if (hasAssignedPort) {
                        robot?.buildStatus = BuildStatus.COMPLETED
                        updateRobot(finished)
                    }
                } else {
                    finished?.invoke()
                }
            }, onError = {
                finished?.invoke()
            })
        }
    }

    private fun collectVariableNames(excludedPortName: String): List<String> {
        userConfiguration?.mappingId?.apply {
            val variableNames = mutableListOf(
                M1?.variableName,
                M2?.variableName,
                M3?.variableName,
                M4?.variableName,
                M5?.variableName,
                M6?.variableName,
                S1?.variableName,
                S2?.variableName,
                S3?.variableName,
                S4?.variableName
            )
            excludeVariableName(this, excludedPortName, variableNames)
            return variableNames.mapNotNull { it }
        }
        return emptyList()
    }

    @Suppress("ComplexMethod")
    private fun excludeVariableName(userMapping: UserMapping, portName: String, variableNames: MutableList<String?>) {
        when (portName) {
            "S1" -> variableNames.remove(userMapping.S1?.variableName)
            "S2" -> variableNames.remove(userMapping.S2?.variableName)
            "S3" -> variableNames.remove(userMapping.S3?.variableName)
            "S4" -> variableNames.remove(userMapping.S4?.variableName)
            "M1" -> variableNames.remove(userMapping.M1?.variableName)
            "M2" -> variableNames.remove(userMapping.M2?.variableName)
            "M3" -> variableNames.remove(userMapping.M3?.variableName)
            "M4" -> variableNames.remove(userMapping.M4?.variableName)
            "M5" -> variableNames.remove(userMapping.M5?.variableName)
            "M6" -> variableNames.remove(userMapping.M6?.variableName)
        }
    }
}
