package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserProgramDao
import com.revolution.robotics.core.interactor.helper.UserControllerHelper
import com.revolution.robotics.features.play.FullControllerData

class GetFullConfigurationInteractor(
    private val userConfigurationDao: UserConfigurationDao,
    private val userControllerDao: UserControllerDao,
    private val userProgramBindingDao: UserBackgroundProgramBindingDao,
    private val userProgramDao: UserProgramDao
) : Interactor<FullControllerData>() {

    var userConfigId = 0

    private val helper = UserControllerHelper()

    override fun getData(): FullControllerData {
        val config = userConfigurationDao.getUserConfiguration(userConfigId)
        val controller = config?.controller?.let { controllerId ->
            helper.getUserController(controllerId, userControllerDao, userProgramBindingDao, userProgramDao)
        }
        val sources = HashMap<String, String>()
        controller?.programs?.keys?.forEach { programName ->
            sources[programName] = controller.programs[programName]?.python ?: ""
        }

        return FullControllerData(config, controller, sources)
    }
}