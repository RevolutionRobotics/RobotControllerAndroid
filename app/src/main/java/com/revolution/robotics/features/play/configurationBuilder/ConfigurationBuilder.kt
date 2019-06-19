package com.revolution.robotics.features.play.configurationBuilder

import com.revolution.robotics.core.domain.local.UserBackgroundProgramBinding
import com.revolution.robotics.core.domain.local.UserProgramBinding
import com.revolution.robotics.core.domain.remote.Controller
import com.revolution.robotics.core.domain.remote.Motor
import com.revolution.robotics.core.domain.remote.Sensor
import com.revolution.robotics.features.play.FullControllerData
import org.json.JSONArray
import org.json.JSONObject

class ConfigurationBuilder {

    private enum class Key(val jsonKey: String? = null) {
        ROBOT_CONFIG("robotConfig"),
        MOTORS,
        NAME,
        TYPE,
        DIRECTION,
        SIDE,
        SENSORS,
        BLOCKLY_LIST("blocklyList"),
        PYTHON_CODE("pythonCode"),
        ASSIGNMENTS,
        BUTTONS,
        ID,
        PRIORITY,
        BUILTIN_SCRIPT_NAME("builtinScriptName"),
        ANALOG,
        CHANNELS,
        BACKGROUND;

        override fun toString() =
            if (jsonKey == null) {
                Regex("[^A-Za-z]").replace(super.toString(), "").toLowerCase()
            } else {
                jsonKey
            }
    }

    @Suppress("ComplexMethod")
    fun createConfigurationJson(data: FullControllerData): String {
        val controller = data.controller
        val configuration = data.userConfiguration

        if (controller != null && configuration != null) {
            val motors = JSONArray().apply {
                listOf(
                    configuration.mappingId?.M1,
                    configuration.mappingId?.M2,
                    configuration.mappingId?.M3,
                    configuration.mappingId?.M4,
                    configuration.mappingId?.M5,
                    configuration.mappingId?.M6
                ).forEach { put(motorToJson(it)) }
            }
            val sensors = JSONArray().apply {
                listOf(
                    configuration.mappingId?.S1,
                    configuration.mappingId?.S2,
                    configuration.mappingId?.S3,
                    configuration.mappingId?.S4
                ).forEach { put(sensorToJson(it)) }
            }
            val blocklyList = JSONArray().apply {
                listOf(
                    controller.userController.mapping?.b1,
                    controller.userController.mapping?.b2,
                    controller.userController.mapping?.b3,
                    controller.userController.mapping?.b4,
                    controller.userController.mapping?.b5,
                    controller.userController.mapping?.b6
                ).forEachIndexed { index, binding -> binding?.let { put(buttonToJson(it, data.sources, index)) } }
                controller.backgroundBindings.forEach { put(backgroundProgramToJson(it, data.sources)) }

                val driveType =
                    if (controller.userController.type == Controller.TYPE_DRIVER) {
                        ConfigurationConstants.DRIVE_TYPE_LEVERS
                    } else {
                        ConfigurationConstants.DRIVE_TYPE_JOYSTICK
                    }
                put(joystickToJson(driveType, controller.userController.joystickPriority))
            }

            return JSONObject().apply {
                put(Key.ROBOT_CONFIG,
                    JSONObject().apply {
                        put(Key.MOTORS, motors)
                        put(Key.SENSORS, sensors)
                    })
                put(Key.BLOCKLY_LIST, blocklyList)
            }.toString()
        } else {
            throw IllegalArgumentException("Controller and Configuration fields cannot be null")
        }
    }

    private fun motorToJson(motor: Motor?) =
        JSONObject().apply {
            if (motor != null) {
                put(Key.NAME, motor.variableName)
                put(Key.TYPE, ConfigurationConstants.get(motor.type))
                put(Key.DIRECTION, ConfigurationConstants.get(motor.direction))
                put(Key.SIDE, ConfigurationConstants.get(motor.side))
            }
        }

    private fun sensorToJson(sensor: Sensor?) =
        JSONObject().apply {
            if (sensor != null) {
                put(Key.NAME, sensor.variableName)
                put(Key.TYPE, ConfigurationConstants.get(sensor.type))
            }
        }

    private fun buttonToJson(binding: UserProgramBinding, sources: HashMap<String, String>, buttonId: Int) =
        JSONObject().apply {
            put(Key.PYTHON_CODE, sources[binding.programName])
            val assignment = JSONObject().apply {
                put(Key.ID, buttonId)
                put(Key.PRIORITY, binding.priority)
            }
            val buttons = JSONArray().apply { put(assignment) }
            val assignmentWrapper = JSONObject().apply { put(Key.BUTTONS, buttons) }
            put(Key.ASSIGNMENTS, assignmentWrapper)
        }

    private fun backgroundProgramToJson(binding: UserBackgroundProgramBinding, sources: HashMap<String, String>) =
        JSONObject().apply {
            put(Key.PYTHON_CODE, sources[binding.programId])
            val assignmentWrapper = JSONObject().apply { put(Key.BACKGROUND, binding.priority) }
            put(Key.ASSIGNMENTS, assignmentWrapper)
        }

    private fun joystickToJson(driveType: String, priority: Int) =
        JSONObject().apply {
            put(Key.BUILTIN_SCRIPT_NAME, driveType)
            val joystick = JSONObject().apply {
                put(Key.CHANNELS,
                    JSONArray().apply {
                        put(0)
                        put(1)
                    })
                put(Key.PRIORITY, priority)
            }
            val analog = JSONArray().apply { put(joystick) }
            val analogWrapper = JSONObject().apply { put(Key.ANALOG, analog) }
            put(Key.ASSIGNMENTS, analogWrapper)
        }

    private fun JSONObject.put(key: Key, value: String?) = put(key.toString(), value)
    private fun JSONObject.put(key: Key, json: JSONObject) = put(key.toString(), json)
    private fun JSONObject.put(key: Key, array: JSONArray) = put(key.toString(), array)
    private fun JSONObject.put(key: Key, value: Int) {
        if (value != ConfigurationConstants.INVALID_DATA) {
            put(key.toString(), value)
        }
    }
}
