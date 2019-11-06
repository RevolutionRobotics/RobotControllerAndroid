package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramDao

class GetUserProgramsForRobotInteractor(private val userProgramDao: UserProgramDao) : Interactor<List<UserProgram>>() {

    var robotId: Int = 0

    override fun getData() = userProgramDao.getUserProgramsForRobot(robotId)
}
