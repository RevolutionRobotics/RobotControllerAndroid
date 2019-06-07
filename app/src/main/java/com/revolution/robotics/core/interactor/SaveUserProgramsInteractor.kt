package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.ProgramLocalFiles
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramDao
import com.revolution.robotics.core.domain.remote.Program

class SaveUserProgramsInteractor(private val userProgramDao: UserProgramDao) : Interactor<Unit>() {

    lateinit var programLocalFiles: List<ProgramLocalFiles>
    lateinit var programs: List<Program>

    override fun getData() {
        programs.forEach { remoteProgram ->
            userProgramDao.saveUserProgram(
                UserProgram(
                    0,
                    remoteProgram.description,
                    remoteProgram.lastModified,
                    remoteProgram.name,
                    programLocalFiles.find { it.remoteId == remoteProgram.id }?.python?.path ?: "",
                    programLocalFiles.find { it.remoteId == remoteProgram.id }?.xml?.path ?: "",
                    remoteProgram.variables,
                    remoteProgram.id
                )
            )
        }
    }
}
