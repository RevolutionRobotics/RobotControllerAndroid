package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramDao

class SaveUserProgramInteractor(private val userProgramDao: UserProgramDao) : Interactor<Long>() {

    lateinit var userProgram: UserProgram

    override fun getData(): Long = userProgramDao.saveUserProgram(userProgram)
}
