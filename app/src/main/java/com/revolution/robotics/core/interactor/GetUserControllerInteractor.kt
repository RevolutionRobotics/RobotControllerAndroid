package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserProgramDao
import com.revolution.robotics.core.interactor.helper.UserControllerHelper

class GetUserControllerInteractor(
    private val userControllerDao: UserControllerDao,
    private val userProgramBindingDao: UserBackgroundProgramBindingDao,
    private val userProgramDao: UserProgramDao
) :
    Interactor<UserControllerWithPrograms>() {

    var id = -1

    private val helper = UserControllerHelper()

    override fun getData() =
        helper.getUserController(id, userControllerDao, userProgramBindingDao, userProgramDao)
}
