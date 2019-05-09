package com.revolution.robotics.features.configure.connections

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.revolution.robotics.R

data class RobotPartModel(
    @StringRes val name: Int = R.string.configure_connections_missing_part_name,
    @ColorRes val color: Int = R.color.grey_6d,
    @DrawableRes val icon: Int = R.drawable.ic_config_add,
    val isActive: Boolean,
    val isSelected: Boolean,
    val onClick: () -> Unit
)
