package com.revolution.robotics.features.controllers.controllerTypeSelector

import com.revolution.robotics.core.utils.Navigator

// TODO remove this suppress
@Suppress("UnusedPrivateMember")
class ControllerTypeSelectorPresenter(private val navigator: Navigator) : ControllerTypeSelectorMvp.Presenter {

    override var view: ControllerTypeSelectorMvp.View? = null
    override var model: ControllerTypeSelectorViewModel? = null

    override fun onControllerTypeSelected(type: ControllerType) {
        // TODO navigate to adequate controller setup screen
    }
}
