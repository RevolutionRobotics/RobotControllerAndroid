package com.revolution.robotics.features.configure.controller

import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserProgram

class CompatibleProgramFilterer {

    fun isProgramCompatible(userProgram: UserProgram, userConfiguration: UserConfiguration?): Boolean {
        val configVariables = userConfiguration?.mappingId?.getVariables() ?: emptyList()
        return userProgram.variables.filter { it.isNotEmpty() }.all { configVariables.contains(it) }
    }

    fun getCompatibleProgramsOnly(programs: List<UserProgram>, userConfiguration: UserConfiguration?): List<UserProgram> =
        programs.filter { isProgramCompatible(it, userConfiguration) }
}
