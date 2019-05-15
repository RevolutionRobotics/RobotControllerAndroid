package com.revolution.robotics.features.play

import androidx.lifecycle.ViewModel

class PlayViewModel(private val presenter: PlayMvp.Presenter) : ViewModel() {

    fun onButtonPressed(ordinal: Int) =
        presenter.onButtonPressed(ordinal)
}
