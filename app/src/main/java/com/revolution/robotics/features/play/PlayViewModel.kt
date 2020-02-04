package com.revolution.robotics.features.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.revolution.robotics.core.domain.local.UserProgram

class PlayViewModel(private val presenter: PlayMvp.Presenter) : ViewModel() {

    companion object {
        private const val PROGRAM_COUNT = 6
    }

    val programs = MutableList<UserProgram?>(PROGRAM_COUNT) { null }
    val onboaringFinished = MutableLiveData<Boolean>()
    val button1Listener = ButtonPressListener { onButtonPressChanged(1, it) }
    val button2Listener = ButtonPressListener { onButtonPressChanged(2, it) }
    val button3Listener = ButtonPressListener { onButtonPressChanged(3, it) }
    val button4Listener = ButtonPressListener { onButtonPressChanged(4, it) }
    val button5Listener = ButtonPressListener { onButtonPressChanged(5, it) }
    val button6Listener = ButtonPressListener { onButtonPressChanged(6, it) }

    fun getProgramLabel(index: Int) =
        programs[index - 1]?.name

    private fun onButtonPressChanged(ordinal: Int, pressed: Boolean) {
        if (pressed) {
            presenter.onButtonPressed(ordinal)
        } else {
            presenter.onButtonReleased(ordinal)
        }
    }
}
