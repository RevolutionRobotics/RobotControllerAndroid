package com.revolution.robotics.views.toolbar

import androidx.annotation.DrawableRes

data class ToolbarOption(
    val id: Int? = null,
    @DrawableRes val icon: Int,
    val onClick: () -> Unit
) {
    constructor(icon: Int, onClick: () -> Unit) :
            this(null, icon, onClick)
}
