package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserConfigurationDao
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.configure.controller.ControllerButton

class AssignProgramToButtonInteractor(
    private val userConfigurationDao: UserConfigurationDao,
    private val userControllerDao: UserControllerDao
) : Interactor<UserController?>() {

    var userConfigurationId: Int = -1
    var button: ControllerButton? = null
    var userProgram: UserProgram? = null

    override fun getData(): UserController? {
        val config = userConfigurationDao.getUserConfiguration(userConfigurationId)
        val controller = config?.controller?.let {
            userControllerDao.getUserController(config.controller!!)}
        if (button != null) {
            if (userProgram != null) {
                controller?.addButtonProgram(userProgram!!, button!!)
            } else {
                controller?.removeButtonProgram(button!!)
            }
        }
        controller?.let { userControllerDao.updateUserController(it) }
        return controller
    }
}
