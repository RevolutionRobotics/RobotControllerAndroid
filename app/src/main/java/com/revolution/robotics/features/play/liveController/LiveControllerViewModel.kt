package com.revolution.robotics.features.play.liveController

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// TODO delete this class
class LiveControllerViewModel(private val presenter: LiveControllerMvp.Presenter) : ViewModel() {

    val connectionState = MutableLiveData<String>()

    fun buttonText(index: Int) = "Button $index"

    fun onButtonClicked(index: Int) {
        presenter.onButtonClicked(index)
    }
}
