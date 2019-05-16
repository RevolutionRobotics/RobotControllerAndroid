package com.revolution.robotics.features.controllers.typeSelector

import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.controllers.ControllerType

// TODO remove this suppress
@Suppress("UnusedPrivateMember")
class TypeSelectorPresenter(private val navigator: Navigator) : TypeSelectorMvp.Presenter {

    override var view: TypeSelectorMvp.View? = null
    override var model: TypeSelectorViewModel? = null

    // TODO navigate to adequate controller setup screen
    override fun onControllerTypeSelected(type: ControllerType) {
        navigator.navigate(
            when (type) {
                ControllerType.GAMER -> TypeSelectorFragmentDirections.toSetupGamer()
                ControllerType.MULTITASKER -> TypeSelectorFragmentDirections.toSetupGamer()
                ControllerType.DRIVER -> TypeSelectorFragmentDirections.toSetupGamer()
            }
        )
    }
}
