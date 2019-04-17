package com.revolution.robotics.controller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveControllerViewModel : ViewModel() {

    val connectionState = MutableLiveData<String>()

    fun buttonText(index: Int) = "Button $index}"
}