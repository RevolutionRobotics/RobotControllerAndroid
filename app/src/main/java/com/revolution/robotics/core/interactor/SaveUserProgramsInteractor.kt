package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramDao
import com.revolution.robotics.core.domain.remote.Program
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class SaveUserProgramsInteractor(
    private val userProgramDao: UserProgramDao,
    private val resourceResolver: ResourceResolver
) : Interactor<Unit>() {

    lateinit var programs: List<Program>

    override fun getData() {
        programs.forEach { remoteProgram ->
            userProgramDao.saveUserProgram(
                UserProgram(
                    remoteProgram.description?.getLocalizedString(resourceResolver) ?: "",
                    remoteProgram.lastModified,
                    remoteProgram.name?.getLocalizedString(resourceResolver) ?: "",
                    remoteProgram.python ?: "",
                    remoteProgram.xml ?: "",
                    remoteProgram.variables,
                    remoteProgram.id
                )
            )
        }
    }
}
