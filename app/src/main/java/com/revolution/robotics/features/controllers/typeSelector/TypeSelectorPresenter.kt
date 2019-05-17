package com.revolution.robotics.features.controllers.typeSelector

import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.controllers.ControllerType

class TypeSelectorPresenter(private val navigator: Navigator) : TypeSelectorMvp.Presenter {

    override var view: TypeSelectorMvp.View? = null
    override var model: TypeSelectorViewModel? = null

    override fun onControllerTypeSelected(type: ControllerType) {
        navigator.navigate(
            when (type) {
                ControllerType.GAMER -> TypeSelectorFragmentDirections.toSetupGamer()
                ControllerType.MULTITASKER -> TypeSelectorFragmentDirections.toSetupMultitasker()
                // TODO navigate to driver controller setup screen
                ControllerType.DRIVER -> TypeSelectorFragmentDirections.toSetupGamer()
            }
        )
    }
}
