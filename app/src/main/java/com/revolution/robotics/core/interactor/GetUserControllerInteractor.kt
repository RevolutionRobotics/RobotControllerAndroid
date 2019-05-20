package com.revolution.robotics.core.interactor

import android.util.SparseArray
import com.revolution.robotics.core.domain.local.UserBackgroundProgramBindingDao
import com.revolution.robotics.core.domain.local.UserController
import com.revolution.robotics.core.domain.local.UserControllerDao
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.domain.local.UserProgramBinding
import com.revolution.robotics.core.domain.local.UserProgramDao

class GetUserControllerInteractor(
    private val userControllerDao: UserControllerDao,
    private val userProgramBindingDao: UserBackgroundProgramBindingDao,
    private val userProgramDao: UserProgramDao
) :
    Interactor<UserControllerWithPrograms>() {

    var id = -1

    override fun getData(): UserControllerWithPrograms {
        val controller =
            userControllerDao.getUserController(id) ?: throw IllegalArgumentException("Controller is missing: $id")

        val backgroundProgramBindings = userProgramBindingDao.getBackgroundPrograms(id)
        val programs = mutableListOf<UserProgram>()
        backgroundProgramBindings.forEach { binding ->
            userProgramDao.getUserProgram(binding.programId)?.let {
                programs.add(it)
            }
        }
        addButtonPrograms(programs, controller)

        return UserControllerWithPrograms(
            controller,
            backgroundProgramBindings.toMutableList(),
            SparseArray<UserProgram>(programs.size).apply {
                programs.forEach { put(it.id, it) }
            })
    }

    private fun addButtonPrograms(programs: MutableList<UserProgram>, userController: UserController) {
        getButtonProgram(userController.mapping?.b1)?.let {
            programs.add(it)
        }
        getButtonProgram(userController.mapping?.b2)?.let {
            programs.add(it)
        }
        getButtonProgram(userController.mapping?.b3)?.let {
            programs.add(it)
        }
        getButtonProgram(userController.mapping?.b4)?.let {
            programs.add(it)
        }
        getButtonProgram(userController.mapping?.b5)?.let {
            programs.add(it)
        }
        getButtonProgram(userController.mapping?.b6)?.let {
            programs.add(it)
        }
    }

    private fun getButtonProgram(programBinding: UserProgramBinding?): UserProgram? = programBinding?.programId?.let {
        userProgramDao.getUserProgram(it)
    }
}
