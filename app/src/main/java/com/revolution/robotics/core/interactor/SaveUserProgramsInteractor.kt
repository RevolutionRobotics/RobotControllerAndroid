package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramDao
import com.revolution.robotics.core.domain.remote.Program

class SaveUserProgramsInteractor(private val userProgramDao: UserProgramDao) : Interactor<Unit>() {

    lateinit var programs: List<Program>

    override fun getData() {
        programs.forEach { remoteProgram ->
            userProgramDao.saveUserProgram(
                UserProgram(
                    remoteProgram.description,
                    remoteProgram.lastModified,
                    remoteProgram.name ?: "",
                    remoteProgram.python ?: "",
                    remoteProgram.xml ?: "",
                    remoteProgram.variables,
                    remoteProgram.id
                )
            )
        }
    }
}
