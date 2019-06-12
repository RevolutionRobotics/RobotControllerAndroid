package com.revolution.robotics.features.myRobots.adapter

import androidx.annotation.DrawableRes
import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.features.myRobots.MyRobotsMvp

@Suppress("DataClassContainsFunctions")
data class MyRobotsItem(
    val id: Int,
    val robot: UserRobot,
    val iconDescription: String,
    val isUnderConstruction: Boolean,
    private val presenter: MyRobotsMvp.Presenter
) {
    val name = robot.name
    val description = robot.description
    val imageUrl = robot.coverImage

    @DrawableRes
    val selectedResource: Int = R.drawable.my_robots_border_red_selector
    @DrawableRes
    val defaultResource: Int = R.drawable.my_robots_border_white
    var isSelected: ObservableBoolean = ObservableBoolean(false)

    fun playButtonText() =
        when {
            isUnderConstruction && robot.isCustomBuild() ->
                R.string.my_robots_under_construction_button_custom
            isUnderConstruction && robot.buildStatus == BuildStatus.INVALID_CONFIGURATION ->
                R.string.my_robots_under_construction_button_custom
            isUnderConstruction ->
                R.string.my_robots_under_construction_button
            else -> R.string.my_robots_play_button
        }

    fun onPlayClicked() {
        if (isUnderConstruction) {
            presenter.onContinueBuildingSelected(robot)
        } else {
            presenter.onPlaySelected(robot.configurationId)
        }
    }

    fun onMoreInfoClicked() {
        presenter.onMoreInfoClicked(robot)
    }
}
