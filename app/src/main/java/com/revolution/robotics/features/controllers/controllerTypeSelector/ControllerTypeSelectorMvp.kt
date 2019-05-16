package com.revolution.robotics.features.controllers.controllerTypeSelector

import com.revolution.robotics.core.Mvp

interface ControllerTypeSelectorMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, ControllerTypeSelectorViewModel>
}
