package com.revolution.robotics.features.whoToBuild.adapter

import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R

data class RobotsAdapterItem(val name: String, val time: String) {
    val selectedResource = R.drawable.robots_card_border_red
    val defaultResource = R.drawable.robots_card_border_grey
    var isSelected: ObservableBoolean = ObservableBoolean(false)
}
