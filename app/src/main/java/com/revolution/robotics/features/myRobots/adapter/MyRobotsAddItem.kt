package com.revolution.robotics.features.myRobots.adapter

import androidx.annotation.DrawableRes
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.features.myRobots.MyRobotsMvp

data class MyRobotsAddItem(
    private val presenter: MyRobotsMvp.Presenter
) : MyRobotsItem(
    0,
    UserRobot(configuration = UserConfiguration()),
    "",
    false,
    presenter
) {
    @DrawableRes
    override val selectedResource: Int = R.drawable.card_border_build_new
    @DrawableRes
    override val defaultResource: Int = R.drawable.card_border_build_new_gray

    override fun onItemClicked() {
        if (isSelected.get()) {
            presenter.navigateToWhoToBuild()
        } else {
            super.onItemClicked()
        }
    }
}