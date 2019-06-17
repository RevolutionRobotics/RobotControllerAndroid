package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserProgramDao
import java.io.File

class RemoveUserProgramInteractor(private val userProgramDao: UserProgramDao) : Interactor<Unit>() {

    lateinit var userProgramName: String

    override fun getData() {
        userProgramDao.getUserProgram(userProgramName)?.let { program ->
            program.xml?.let { xml ->
                File(xml).delete()
            }

            program.python?.let { python ->
                File(python).delete()
            }
        }
        userProgramDao.removeUserProgram(userProgramName)
    }
}
