package com.revolution.robotics.views.dialogs

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DialogButton(
    @StringRes val text: Int,
    @DrawableRes val icon: Int,
    val isHighlighted: Boolean = false,
    val onClick: () -> Unit
)