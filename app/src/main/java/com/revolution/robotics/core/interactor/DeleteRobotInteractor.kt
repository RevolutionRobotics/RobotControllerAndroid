package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserRobotDao

class DeleteRobotInteractor(private val userRobotDao: UserRobotDao) : Interactor<Unit>() {

    var id: Int = -1

    override fun getData() {
        // userRobotDao.deleteRobotById(id)
    }
}
