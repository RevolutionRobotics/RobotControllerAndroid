package com.revolution.robotics.views.toolbar

import androidx.annotation.DrawableRes

data class ToolbarOption(
    @DrawableRes val icon: Int,
    val onClick: () -> Unit
)
