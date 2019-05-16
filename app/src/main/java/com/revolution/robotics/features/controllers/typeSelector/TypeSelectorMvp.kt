package com.revolution.robotics.features.controllers.typeSelector

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.features.controllers.ControllerType

interface TypeSelectorMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, TypeSelectorViewModel> {
        fun onControllerTypeSelected(type: ControllerType)
    }
}
