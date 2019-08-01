package com.revolution.robotics.features.myRobots.adapter

import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.features.myRobots.MyRobotsMvp

data class MyRobotsAddItem(
    private val presenter: MyRobotsMvp.Presenter
) : MyRobotsItem(
    0,
    UserRobot(),
    "",
    false,
    presenter
) {
    override fun onItemClicked() {
        if (isSelected.get()) {
            presenter.navigateToWhoToBuild()
        } else {
            super.onItemClicked()
        }
    }
}