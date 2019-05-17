package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerDao

class GetUserControllersInteractor(private val userControllerDao: UserControllerDao) :
    Interactor<List<UserController>>() {

    override fun getData(): List<UserController> = userControllerDao.getUserControllers()
}
