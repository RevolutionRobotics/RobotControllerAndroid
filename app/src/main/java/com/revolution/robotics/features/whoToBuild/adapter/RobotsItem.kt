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
    val name = if (downloaded) robot.name else LocalizedString("DOWNLOAD")
    val time = robot.buildTime
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
