package com.revolution.robotics.controller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveControllerViewModel(private val presenter: LiveControllerMvp.Presenter) : ViewModel() {

    val connectionState = MutableLiveData<String>()

    fun buttonText(index: Int) = "Button $index"

    fun onButtonClicked(index: Int) {
        presenter.onButtonClicked(index)
    }
}