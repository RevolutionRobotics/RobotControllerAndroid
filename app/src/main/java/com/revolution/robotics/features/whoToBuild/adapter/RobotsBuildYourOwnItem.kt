package com.revolution.robotics.features.whoToBuild.adapter

import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.features.whoToBuild.WhoToBuildMvp

class RobotsBuildYourOwnItem(
    private val presenter: WhoToBuildMvp.Presenter
) : RobotsItem(
    Robot(), presenter
) {
    override fun onClicked() {
        if (isSelected.get()) {
            presenter.onBuildYourOwnSelected()
        } else {
            super.onClicked()
        }
    }
}