package com.revolution.robotics.features.whoToBuild.adapter

import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R
import com.revolution.robotics.features.whoToBuild.WhoToBuildMvp

@Suppress("DataClassContainsFunctions")
data class RobotsItem(
    private val id: Int,
    val name: String,
    val time: String,
    val imageUrl: String,
    private val presenter: WhoToBuildMvp.Presenter
) {
    val selectedResource = R.drawable.robots_card_border_red
    val defaultResource = R.drawable.robots_card_border_grey
    var isSelected: ObservableBoolean = ObservableBoolean(false)

    fun onClicked() {
        presenter.onRobotSelected(id)
    }
}
