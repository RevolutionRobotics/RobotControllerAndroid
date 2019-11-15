package com.revolution.robotics.features.myRobots.adapter

import androidx.annotation.DrawableRes
import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.BuildStatus
import com.revolution.robotics.core.domain.local.UserRobot
import com.revolution.robotics.features.myRobots.MyRobotsMvp

@Suppress("DataClassContainsFunctions")
open class MyRobotsItem(
    val id: Int,
    val robot: UserRobot,
    val iconDescription: String,
    val isUnderConstruction: Boolean,
    private val presenter: MyRobotsMvp.Presenter
) {
    val name = robot.name
    val imageUrl = robot.coverImage

    @DrawableRes
    open val selectedResource: Int = R.drawable.my_robots_border_red_selector
    @DrawableRes
    open val defaultResource: Int = R.drawable.my_robots_border_white
    var isSelected: ObservableBoolean = ObservableBoolean(false)

    fun playButtonText() = if (isUnderConstruction) R.string.my_robots_under_construction_button else R.string.my_robots_play_button

    open fun onItemClicked() {
        if (isSelected.get()) {
            onPlayClicked()
        } else {
            onDisabledItemClicked()
        }
    }

    private fun onPlayClicked() {
        if (isUnderConstruction) {
            presenter.onContinueBuildingSelected(robot)
        } else {
            presenter.onPlaySelected(robot.id)
        }
    }

    private fun onDisabledItemClicked() {
        presenter.onDisabledItemClicked(robot)
    }

    fun onItemLongClicked(): Boolean {
        presenter.onMoreInfoClicked(robot)
        return false
    }

    fun onEditClicked() {
        presenter.onEditSelected(robot)
    }
}
