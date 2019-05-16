package com.revolution.robotics.features.controllers.controllerTypeSelector

import com.revolution.robotics.core.utils.Navigator

class ControllerTypeSelectorPresenter(private val navigator: Navigator) : ControllerTypeSelectorMvp.Presenter {

    override var view: ControllerTypeSelectorMvp.View? = null
    override var model: ControllerTypeSelectorViewModel? = null
}
