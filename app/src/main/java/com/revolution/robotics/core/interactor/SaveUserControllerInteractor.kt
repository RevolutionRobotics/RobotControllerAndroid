package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserBackgroundProgramBinding
import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerDao

class SaveUserControllerInteractor(
    private val userControllerDao: UserControllerDao,
    private val userBackgroundProgramBindingDao: UserBackgroundProgramBindingDao
) : Interactor<Long>() {

    lateinit var userController: UserController
    lateinit var backgroundProgramBindings: List<UserBackgroundProgramBinding>

    override fun getData(): Long {
        val controllerId = userControllerDao.saveUserController(userController)
        backgroundProgramBindings.forEach {
            it.controllerId = controllerId.toInt()
        }
        userBackgroundProgramBindingDao.saveBackgroundPrograms(backgroundProgramBindings)
        return controllerId
    }
}
