package com.revolution.robotics.features.myRobots.adapter

import androidx.annotation.DrawableRes
import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.features.myRobots.MyRobotsMvp

@Suppress("DataClassContainsFunctions")
data class MyRobotsItem(
    private val id: Int,
    val name: String,
    val description: String,
    val iconDescription: String,
    val imageUrl: String,
    val isUnderConstruction: Boolean,
    private val presenter: MyRobotsMvp.Presenter
) {
    @DrawableRes
    val selectedResource: Int = R.drawable.my_robots_border_red
    @DrawableRes
    val defaultResource: Int = R.drawable.my_robots_border_white
    var isSelected: ObservableBoolean = ObservableBoolean(false)

    fun onPlayClicked() {
        presenter.onPlaySelected(id)
    }

    fun onEditClicked() {
        presenter.onEditSelected(id)
    }

    fun onDeleteClicked() {
        presenter.onDeleteSelected(id)
    }
}
