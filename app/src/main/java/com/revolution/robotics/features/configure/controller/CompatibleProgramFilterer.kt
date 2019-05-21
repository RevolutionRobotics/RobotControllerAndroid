package com.revolution.robotics.features.configure.controller

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.configure.UserConfigurationStorage

class CompatibleProgramFilterer(private val configurationStorage: UserConfigurationStorage) {

    fun isProgramCompatible(userProgram: UserProgram): Boolean {
        val configVariables = configurationStorage.userConfiguration?.mappingId?.getVariables() ?: emptyList()
        userProgram.variables.forEach { variableName ->
            if (!configVariables.contains(variableName)) {
                return false
            }
        }
        return true
    }

    fun getCompatibleProgramsOnly(programs: List<UserProgram>): List<UserProgram> =
        programs.filter { isProgramCompatible(it) }
}
