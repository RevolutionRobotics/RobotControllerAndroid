package com.revolution.robotics.features.controllers.setup

import com.revolution.robotics.core.Mvp

interface SetupMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, SetupViewModel>
}