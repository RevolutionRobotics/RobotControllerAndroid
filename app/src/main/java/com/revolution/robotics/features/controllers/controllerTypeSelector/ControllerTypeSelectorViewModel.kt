package com.revolution.robotics.features.controllers.controllerTypeSelector

import androidx.lifecycle.ViewModel

class ControllerTypeSelectorViewModel(private val presenter: ControllerTypeSelectorMvp.Presenter) : ViewModel() {

    fun onGamerControllerSelected() {
        presenter.onControllerTypeSelected(ControllerType.GAMER)
    }

    fun onMultitaskerControllerSelected() {
        presenter.onControllerTypeSelected(ControllerType.MULTITASKER)
    }

    fun onDriverControllerSelected() {
        presenter.onControllerTypeSelected(ControllerType.DRIVER)
    }
}
