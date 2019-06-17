package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramDao

class SaveUserProgramInteractor(private val userProgramDao: UserProgramDao) : Interactor<Unit>() {

    lateinit var userProgram: UserProgram
    var clearRemoteId = false

    override fun getData() {
        if (clearRemoteId) {
            userProgram.remoteId = null
        }
        userProgramDao.saveUserProgram(userProgram)
    }
}
