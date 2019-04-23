package com.revolution.robotics.features.robots.adapter

import androidx.annotation.DrawableRes
import androidx.databinding.ObservableBoolean
import com.revolution.robotics.R

data class RobotsAdapterItem(
    val name: String,
    val time: String,
    @DrawableRes
    val selectedResource: Int = R.drawable.robots_card_border_red,
    @DrawableRes
    val defaultResource: Int = R.drawable.robots_card_border_grey
) {
    var isSelected: ObservableBoolean = ObservableBoolean(false)
}
