package com.revolution.robotics.features.controllers.setup

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.local.UserControllerWithPrograms
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.features.controllers.ControllerType

class ConfigureControllerViewModel(private val presenter: ConfigureControllerMvp.Presenter) : ViewModel() {

    companion object {
        const val NO_PROGRAM_SELECTED = -1
    }

    private val programs = mutableListOf<UserProgram?>(null, null, null, null, null, null)
    var selectedProgram = NO_PROGRAM_SELECTED
    var controllerType: ControllerType? = null

    fun isProgramSelected(index: Int) = selectedProgram == index

    fun getProgram(index: Int) =
        if (programs.size >= index && index >= 0) {
            programs[index - 1]
        } else {
            null
        }

    fun getProgramIndex(program: UserProgram) =
        programs.indexOf(program)

    fun onProgramSet(program: UserProgram?) {
        programs[selectedProgram - 1] = program
    }

    fun selectProgram(index: Int) {
        selectedProgram = index
        presenter.onProgramSlotSelected(index)
    }

    fun onProgramLongClicked(index: Int) : Boolean {
        selectedProgram = index
        getProgram(index)?.let {
            presenter.onProgramSelected(it, true)
            return true
        }
        return false
    }

    fun update(userControllerWithPrograms: UserControllerWithPrograms) {
        controllerType = ControllerType.fromId(userControllerWithPrograms.userController.type)!!
        userControllerWithPrograms.userController.getMappingList().forEachIndexed { index, userProgramBinding ->
            userProgramBinding?.programName?.let { programId ->
                programs[index] = userControllerWithPrograms.programs[programId]
            }
        }
    }

    fun play() = presenter.play()
}
