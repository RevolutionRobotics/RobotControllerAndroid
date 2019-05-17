package com.revolution.robotics.features.controllers.typeSelector

import androidx.lifecycle.ViewModel
import com.revolution.robotics.features.controllers.ControllerType

class TypeSelectorViewModel(private val presenter: TypeSelectorMvp.Presenter) : ViewModel() {

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
