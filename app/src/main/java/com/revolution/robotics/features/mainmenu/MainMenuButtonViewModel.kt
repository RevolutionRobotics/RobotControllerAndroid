package com.revolution.robotics.features.mainmenu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainMenuButtonViewModel(
    @StringRes val text: Int,
    @DrawableRes val background: Int,
    @DrawableRes val image: Int,
    val onClick: () -> Unit
)
