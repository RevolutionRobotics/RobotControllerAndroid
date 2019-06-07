package com.revolution.robotics.features.configure.controller

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.configure.UserConfigurationStorage

class CompatibleProgramFilterer(private val configurationStorage: UserConfigurationStorage) {

    fun isProgramCompatible(userProgram: UserProgram): Boolean {
        val configVariables = configurationStorage.userConfiguration?.mappingId?.getVariables() ?: emptyList()
        // TODO remove this filter when blockly-js is fixed
        return userProgram.variables.filter { it.isNotEmpty() }.all { configVariables.contains(it) }
    }

    fun getCompatibleProgramsOnly(programs: List<UserProgram>): List<UserProgram> =
        programs.filter { isProgramCompatible(it) }
}
