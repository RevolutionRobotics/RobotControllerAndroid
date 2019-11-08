package com.revolution.robotics.core.interactor

import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserButtonMapping
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserProgramDao

class RemoveUserProgramInteractor(
    private val userProgramDao: UserProgramDao,
    private val controllerDao: UserControllerDao,
    private val backgroundProgramBindingDao: UserBackgroundProgramBindingDao
) : Interactor<Unit>() {

    lateinit var userProgramName: String
    var robotId: Int = 0

    override fun getData() {
        controllerDao.getUserControllersForRobot(robotId).forEach { controller ->
            removeProgramFromMapping(controller.mapping)
            controllerDao.updateUserController(controller)
            backgroundProgramBindingDao.removeBackgroundProgram(userProgramName, controller.id)
        }
        userProgramDao.removeUserProgram(userProgramName, robotId)
    }

    private fun removeProgramFromMapping(mapping: UserButtonMapping?) {
        if (mapping?.b1?.programName == userProgramName) {
            mapping.b1 = null
        }
        if (mapping?.b2?.programName == userProgramName) {
            mapping.b2 = null
        }
        if (mapping?.b3?.programName == userProgramName) {
            mapping.b3 = null
        }
        if (mapping?.b4?.programName == userProgramName) {
            mapping.b4 = null
        }
        if (mapping?.b5?.programName == userProgramName) {
            mapping.b5 = null
        }
        if (mapping?.b6?.programName == userProgramName) {
            mapping.b6 = null
        }
    }
}
