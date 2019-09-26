package com.revolution.robotics.features.onboarding.carby.haveyoubuilt

import androidx.lifecycle.ViewModel

class HaveYouBuiltCarbyViewModel(private val presenter: HaveYouBuiltCarbyMvp.Presenter): ViewModel() {
    fun buildCarby() = presenter.buildCarby()
    fun driveCarby() = presenter.driveCarby()
    fun skipOnboarding() = presenter.skipOnboarding()
}