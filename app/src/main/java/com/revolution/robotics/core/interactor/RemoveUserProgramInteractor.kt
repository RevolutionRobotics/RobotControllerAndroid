package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserProgramDao
import java.io.File

class RemoveUserProgramInteractor(private val userProgramDao: UserProgramDao) : Interactor<Unit>() {

    var userProgramId = -1

    override fun getData() {
        userProgramDao.getUserProgram(userProgramId)?.let { program ->
            program.xml?.let { xml ->
                File(xml).delete()
            }

            program.python?.let { python ->
                File(python).delete()
            }
        }
        userProgramDao.removeUserProgram(userProgramId)
    }
}
