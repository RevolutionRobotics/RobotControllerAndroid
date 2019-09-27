package com.revolution.robotics.features.onboarding.haveyoubuilt

import androidx.lifecycle.ViewModel

class HaveYouBuiltViewModel(private val presenter: HaveYouBuiltMvp.Presenter): ViewModel() {
    fun buildRobot() = presenter.buildRobot()
    fun driveRobot() = presenter.driveRobot()
    fun skipOnboarding() = presenter.skipOnboarding()
}