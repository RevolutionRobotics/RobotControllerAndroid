package com.revolution.robotics.features.whoToBuild.adapter

import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.features.whoToBuild.WhoToBuildMvp

open class RobotsItem(
    val robot: Robot,
    private val presenter: WhoToBuildMvp.Presenter
) {
    val name = robot.name
    val time = robot.buildTime
    val imageUrl = robot.coverImage
    open val selectedResource = R.drawable.robots_card_border_red_selector
    open val defaultResource = R.drawable.robots_card_border_grey
    var isSelected: ObservableBoolean = ObservableBoolean(false)

    open fun onClicked() {
        if (isSelected.get()) {
            presenter.onRobotSelected(robot)
        } else {
            presenter.onDisabledItemClicked(this)
        }
    }
}
