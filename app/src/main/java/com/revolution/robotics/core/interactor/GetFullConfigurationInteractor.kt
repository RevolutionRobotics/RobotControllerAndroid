package com.revolution.robotics.core.interactor

import android.util.Base64
import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserProgramDao
import com.revolution.robotics.core.interactor.helper.UserControllerHelper
import com.revolution.robotics.features.play.FullControllerData
import java.io.File

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
            val pythonFile = controller.programs[programName]
            val file = File(pythonFile?.python)
            if (file.exists()) {
                sources[programName] = file.readText().encode()
            }
        }

        return FullControllerData(config, controller, sources)
    }

    private fun String.encode() =
        String(Base64.encode(toByteArray(), Base64.NO_WRAP))
}
