package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserControllerDao

class RemoveUserControllerInteractor(private val userControllerDao: UserControllerDao) : Interactor<Unit>() {

    var controllerId = -1

    override fun getData() = userControllerDao.deleteUserControllerById(controllerId)
}
