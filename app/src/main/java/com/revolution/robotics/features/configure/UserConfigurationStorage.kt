package com.revolution.robotics.features.configure

import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserBackgroundProgramBinding
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserMapping
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramBinding
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.interactor.SaveUserControllerInteractor
import com.revolution.robotics.core.interactor.UpdateUserRobotInteractor
import com.revolution.robotics.features.configure.controller.ControllerButton

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

    fun updateRobot() {
        userConfiguration?.let { config ->
            robot?.let { robot ->
                updateUserRobotInteractor.userConfiguration = config
                updateUserRobotInteractor.userRobot = robot
                updateUserRobotInteractor.execute()
            }
        }
    }

    fun getDefaultDrivetrainName(): String {
        var count = 0
        userConfiguration?.mappingId?.let {mapping ->
            if (mapping.M1?.type == Motor.TYPE_DRIVETRAIN) count++
            if (mapping.M2?.type == Motor.TYPE_DRIVETRAIN) count++
            if (mapping.M3?.type == Motor.TYPE_DRIVETRAIN) count++
            if (mapping.M4?.type == Motor.TYPE_DRIVETRAIN) count++
            if (mapping.M5?.type == Motor.TYPE_DRIVETRAIN) count++
            if (mapping.M6?.type == Motor.TYPE_DRIVETRAIN) count++
        }
        return "drivetrain${count + 1}"
    }

    fun updateSensorPort(sensorPort: SensorPort) {
        userConfiguration?.let { config ->
            config.mappingId?.updateSensorPort(sensorPort)
        }
        updateRobot()
    }

    fun updateMotorPort(motorPort: MotorPort) {
        userConfiguration?.let { config ->
            config.mappingId?.updateMotorPort(motorPort)
        }
        updateRobot()
    }

    fun changeController(controllerId: Int) {
        userConfiguration?.controller = controllerId
        updateRobot()
    }

    fun addButtonProgram(userProgram: UserProgram, buttonName: ControllerButton) {
        when (buttonName) {
            ControllerButton.B1 -> controllerHolder?.userController?.mapping?.b1 =
                getNewProgramBinding(userProgram, controllerHolder?.userController?.mapping?.b1)
            ControllerButton.B2 -> controllerHolder?.userController?.mapping?.b2 =
                getNewProgramBinding(userProgram, controllerHolder?.userController?.mapping?.b2)
            ControllerButton.B3 -> controllerHolder?.userController?.mapping?.b3 =
                getNewProgramBinding(userProgram, controllerHolder?.userController?.mapping?.b3)
            ControllerButton.B4 -> controllerHolder?.userController?.mapping?.b4 =
                getNewProgramBinding(userProgram, controllerHolder?.userController?.mapping?.b4)
            ControllerButton.B5 -> controllerHolder?.userController?.mapping?.b5 =
                getNewProgramBinding(userProgram, controllerHolder?.userController?.mapping?.b5)
            ControllerButton.B6 -> controllerHolder?.userController?.mapping?.b6 =
                getNewProgramBinding(userProgram, controllerHolder?.userController?.mapping?.b6)
        }
        controllerHolder?.programs?.put(userProgram.name, userProgram)
        updateUserController()
    }

    fun removeButtonProgram(button: ControllerButton) {
        when (button) {
            ControllerButton.B1 -> {
                removeUserProgram(controllerHolder?.userController?.mapping?.b1)
                controllerHolder?.userController?.mapping?.b1 = null
            }
            ControllerButton.B2 -> {
                removeUserProgram(controllerHolder?.userController?.mapping?.b2)
                controllerHolder?.userController?.mapping?.b2 = null
            }
            ControllerButton.B3 -> {
                removeUserProgram(controllerHolder?.userController?.mapping?.b3)
                controllerHolder?.userController?.mapping?.b3 = null
            }
            ControllerButton.B4 -> {
                removeUserProgram(controllerHolder?.userController?.mapping?.b4)
                controllerHolder?.userController?.mapping?.b4 = null
            }
            ControllerButton.B5 -> {
                removeUserProgram(controllerHolder?.userController?.mapping?.b5)
                controllerHolder?.userController?.mapping?.b5 = null
            }
            ControllerButton.B6 -> {
                removeUserProgram(controllerHolder?.userController?.mapping?.b6)
                controllerHolder?.userController?.mapping?.b6 = null
            }
        }
        updateUserController()
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

    fun removeBackgroundProgram(userProgram: UserProgram) {
        controllerHolder?.backgroundBindings?.removeAll { it.programId == userProgram.name }
        controllerHolder?.programs?.remove(userProgram.name)
    }

    fun clearBackgroundPrograms() {
        controllerHolder?.backgroundBindings?.forEach {
            controllerHolder?.programs?.remove(it.programId)
        }
        controllerHolder?.backgroundBindings?.clear()
    }

    fun setPriority(userProgram: UserProgram, priority: Int) {
        controllerHolder?.backgroundBindings?.find { it.programId == userProgram.name }?.let {
            it.priority = priority
        }

        controllerHolder?.userController?.getMappingList()?.forEach { binding ->
            if (binding?.programName == userProgram.name) {
                binding.priority = priority
            }
        }
        updateUserController()
    }

    fun setRobotName(name: String, description: String) {
        robot?.name = name
        robot?.description = description
        updateRobot()
    }

    fun setControllerName(name: String, description: String) {
        controllerHolder?.userController?.name = name
        controllerHolder?.userController?.description = description
        updateUserController()
    }

    private fun updateUserController() {
        controllerHolder?.userController?.let { userController ->
            saveUserControllerInteractor.userController = userController
            saveUserControllerInteractor.backgroundProgramBindings = controllerHolder?.backgroundBindings ?: emptyList()
            userController.robotId = robot?.instanceId ?: 0
            saveUserControllerInteractor.execute { controller ->
                if (userConfiguration?.controller == null || userConfiguration?.controller == -1) {
                    userConfiguration?.controller = controller.id
                    val hasAssignedPort = !userConfiguration?.mappingId?.getVariables()?.firstOrNull().isNullOrEmpty()
                    if (hasAssignedPort) {
                        robot?.buildStatus = BuildStatus.COMPLETED
                        updateRobot()
                    }
                }
            }
        }
    }

    private fun removeUserProgram(currentBinding: UserProgramBinding?) {
        if (currentBinding != null) {
            controllerHolder?.programs?.remove(currentBinding.programName)
        }
    }

    private fun getNewProgramBinding(newProgram: UserProgram, currentBinding: UserProgramBinding?): UserProgramBinding {
        removeUserProgram(currentBinding)
        return UserProgramBinding(0, controllerHolder?.userController?.id ?: 0, newProgram.name, -1)
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
