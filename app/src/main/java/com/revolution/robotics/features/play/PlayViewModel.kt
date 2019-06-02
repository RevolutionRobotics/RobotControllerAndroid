package com.revolution.robotics.features.play

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.local.UserProgram

class PlayViewModel(private val presenter: PlayMvp.Presenter) : ViewModel() {

    companion object {
        private const val PROGRAM_COUNT = 6
    }

    val programs = MutableList<UserProgram?>(PROGRAM_COUNT) { null }

    fun getProgramLabel(index: Int) =
        programs[index - 1]?.name

    fun onButtonPressed(ordinal: Int) =
        presenter.onButtonPressed(ordinal)
}
