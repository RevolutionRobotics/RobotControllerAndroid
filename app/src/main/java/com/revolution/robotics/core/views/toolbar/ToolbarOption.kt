package com.revolution.robotics.core.views.toolbar

import androidx.annotation.DrawableRes

data class ToolbarOption(
    @DrawableRes val icon: Int,
    val onClick: () -> Unit
)
