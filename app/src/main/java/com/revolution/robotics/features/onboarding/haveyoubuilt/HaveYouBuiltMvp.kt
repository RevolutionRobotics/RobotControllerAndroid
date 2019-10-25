package com.revolution.robotics.features.onboarding.haveyoubuilt

import com.revolution.robotics.core.Mvp

interface HaveYouBuiltMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, HaveYouBuiltViewModel> {
        fun driveRobot()
        fun buildRobot()
        fun skipOnboarding()
    }
}