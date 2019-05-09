package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserConfigurationDao

class GetUserConfigurationInteractor(private val userConfigurationDao: UserConfigurationDao) :
    Interactor<UserConfiguration?>() {

    var userConfigId = 0

    override fun getData(): UserConfiguration? =
        userConfigurationDao.getUserConfiguration(userConfigId)
}
