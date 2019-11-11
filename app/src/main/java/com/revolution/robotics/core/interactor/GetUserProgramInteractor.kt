package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramDao

class GetUserProgramInteractor(private val userProgramDao: UserProgramDao) : Interactor<UserProgram?>() {

    lateinit var name: String
    var robotId: Int = 0

    override fun getData(): UserProgram? = userProgramDao.getUserProgram(name, robotId)
}
