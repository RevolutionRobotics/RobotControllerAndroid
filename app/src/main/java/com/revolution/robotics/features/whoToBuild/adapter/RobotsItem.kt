package com.revolution.robotics.features.whoToBuild.adapter

import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.remote.LocalizedString
import com.revolution.robotics.core.domain.remote.Robot
import com.revolution.robotics.features.whoToBuild.WhoToBuildMvp

open class RobotsItem(
    val robot: Robot,
    val imagePath: String?,
    val downloaded: Boolean,
    private val presenter: WhoToBuildMvp.Presenter
) {
    val name = robot.name
    val time = robot.buildTime
    open val selectedResource = if (downloaded) R.drawable.robots_card_border_red_selector else R.drawable.robots_download_card_border_red_pressed
    open val defaultResource = if (downloaded) R.drawable.robots_card_border_grey else R.drawable.robots_download_card_border_grey
    var isSelected: ObservableBoolean = ObservableBoolean(false)

    open fun onClicked() {
        if (isSelected.get()) {
            presenter.onRobotSelected(robot)
        } else {
            presenter.onDisabledItemClicked(this)
        }
    }
}
