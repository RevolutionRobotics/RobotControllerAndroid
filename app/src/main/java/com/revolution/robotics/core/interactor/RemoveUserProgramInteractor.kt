package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserProgramDao

class RemoveUserProgramInteractor(private val userProgramDao: UserProgramDao) : Interactor<Unit>() {

    var userProgramId = -1

    override fun getData() = userProgramDao.removeUserProgram(userProgramId)
}
