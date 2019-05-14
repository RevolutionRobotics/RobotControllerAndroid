package com.revolution.robotics.features.play

import androidx.lifecycle.ViewModel

@Suppress("OptionalUnit")
class PlayViewModel(private val presenter: PlayMvp.Presenter) : ViewModel() {

    fun onJoystickEvent() =
        presenter.onJoystickEvent()

    fun onButtonPressed(ordinal: Int) =
        presenter.onButtonPressed(ordinal)
}
