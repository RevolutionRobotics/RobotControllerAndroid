package com.revolution.robotics.features.controllers.typeSelector

import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.controllers.ControllerType

class TypeSelectorPresenter(
    private val navigator: Navigator
) : TypeSelectorMvp.Presenter {

    override var view: TypeSelectorMvp.View? = null
    override var model: TypeSelectorViewModel? = null

    override fun onControllerTypeSelected(type: ControllerType) {

    }
}
