package com.revolution.robotics.features.configure

import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserMapping

class UserConfigurationStorage {

    companion object {
        const val ALLOWED_DIGITS_REGEXP = "[a-zA-Z0-9]+"
    }

    var userConfiguration: UserConfiguration? = null

    fun isUsedVariableName(name: String, portName: String): Boolean = collectVariableNames(portName).contains(name)

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