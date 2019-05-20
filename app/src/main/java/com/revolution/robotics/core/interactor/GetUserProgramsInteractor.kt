package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramDao

class GetUserProgramsInteractor(private val userProgramDao: UserProgramDao) : Interactor<List<UserProgram>>() {

    override fun getData() = userProgramDao.getAllPrograms()
}
