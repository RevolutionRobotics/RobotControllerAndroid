package com.revolution.robotics.features.onboarding.carby.haveyoubuilt

import com.revolution.robotics.core.Mvp

interface HaveYouBuiltCarbyMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, HaveYouBuiltCarbyViewModel> {
        fun driveCarby()
        fun buildCarby()
        fun skipOnboarding()
    }
}