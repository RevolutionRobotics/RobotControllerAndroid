package com.revolution.robotics.features.whoToBuild.adapter

import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.features.whoToBuild.WhoToBuildMvp

@Suppress("DataClassContainsFunctions")
data class RobotsItem(
    val robot: Robot,
    private val presenter: WhoToBuildMvp.Presenter
) {
    val name = robot.name
    val time = robot.buildTime
    val imageUrl = robot.coverImage
    val selectedResource = R.drawable.robots_card_border_red
    val defaultResource = R.drawable.robots_card_border_grey
    var isSelected: ObservableBoolean = ObservableBoolean(false)

    fun onClicked() = presenter.onRobotSelected(robot)
}