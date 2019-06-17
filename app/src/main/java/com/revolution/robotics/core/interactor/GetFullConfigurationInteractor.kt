package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserProgramDao
import com.revolution.robotics.core.interactor.helper.UserControllerHelper

class GetFullConfigurationInteractor(
    private val userConfigurationDao: UserConfigurationDao,
    private val userControllerDao: UserControllerDao,
    private val userProgramBindingDao: UserBackgroundProgramBindingDao,
    private val userProgramDao: UserProgramDao
) : Interactor<Pair<UserConfiguration?, UserControllerWithPrograms?>>() {

    var userConfigId = 0

    private val helper = UserControllerHelper()

    override fun getData(): Pair<UserConfiguration?, UserControllerWithPrograms?> {
        val config = userConfigurationDao.getUserConfiguration(userConfigId)
        val controller = config?.controller?.let { controllerId ->
            helper.getUserController(controllerId, userControllerDao, userProgramBindingDao, userProgramDao)
        }
        // TODO load program source codes

        return config to controller
    }
}
