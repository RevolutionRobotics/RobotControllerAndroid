package com.revolution.robotics.core.dialogs

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DialogButtonViewModel(
    @StringRes val text: Int,
    @DrawableRes val icon: Int,
    val onClick: () -> Unit
)
