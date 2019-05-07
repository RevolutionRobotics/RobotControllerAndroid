package com.revolution.robotics.features.myRobots.adapter

import androidx.annotation.DrawableRes
import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.shared.RobotDescriptor
import com.revolution.robotics.features.myRobots.MyRobotsMvp

@Suppress("DataClassContainsFunctions")
data class MyRobotsItem(
    val id: Int,
    val robot: RobotDescriptor,
    val iconDescription: String,
    val isUnderConstruction: Boolean,
    private val presenter: MyRobotsMvp.Presenter
) {
    val name = robot.name
    val description = robot.description
    val imageUrl = robot.coverImage

    @DrawableRes
    val selectedResource: Int = R.drawable.my_robots_border_red
    @DrawableRes
    val defaultResource: Int = R.drawable.my_robots_border_white
    var isSelected: ObservableBoolean = ObservableBoolean(false)

    fun onPlayClicked() {
        if (isUnderConstruction) {
            presenter.onContinueBuildingSelected(robot)
        } else {
            presenter.onPlaySelected(id)
        }
    }

    fun onEditClicked() {
        presenter.onEditSelected(id)
    }

    fun onDeleteClicked() {
        presenter.onDeleteSelected(id)
    }
}
