package com.revolution.robotics.core.interactor.helper

import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramBinding
import com.revolution.robotics.core.domain.local.UserProgramDao

class UserControllerHelper {

    fun getUserController(
        id: Int,
        userControllerDao: UserControllerDao,
        userProgramBindingDao: UserBackgroundProgramBindingDao,
        userProgramDao: UserProgramDao
    ): UserControllerWithPrograms {
        val controller =
            userControllerDao.getUserController(id) ?: throw IllegalArgumentException("Controller is missing: $id")

        val backgroundProgramBindings = userProgramBindingDao.getBackgroundPrograms(id)
        val programs = mutableListOf<UserProgram>()
        backgroundProgramBindings.forEach { binding ->
            userProgramDao.getUserProgram(binding.programId)?.let {
                programs.add(it)
            }
        }
        addButtonPrograms(programs, controller, userProgramDao)

        return UserControllerWithPrograms(
            controller,
            backgroundProgramBindings.toMutableList(),
            hashMapOf<String, UserProgram>().apply {
                programs.forEach { put(it.name, it) }
            })
    }

    private fun addButtonPrograms(
        programs: MutableList<UserProgram>,
        userController: UserController,
        userProgramDao: UserProgramDao
    ) {
        getButtonProgram(userController.mapping?.b1, userProgramDao)?.let {
            programs.add(it)
        }
        getButtonProgram(userController.mapping?.b2, userProgramDao)?.let {
            programs.add(it)
        }
        getButtonProgram(userController.mapping?.b3, userProgramDao)?.let {
            programs.add(it)
        }
        getButtonProgram(userController.mapping?.b4, userProgramDao)?.let {
            programs.add(it)
        }
        getButtonProgram(userController.mapping?.b5, userProgramDao)?.let {
            programs.add(it)
        }
        getButtonProgram(userController.mapping?.b6, userProgramDao)?.let {
            programs.add(it)
        }
    }

    private fun getButtonProgram(programBinding: UserProgramBinding?, userProgramDao: UserProgramDao): UserProgram? =
        programBinding?.programName?.let {
            userProgramDao.getUserProgram(it)
        }
}
