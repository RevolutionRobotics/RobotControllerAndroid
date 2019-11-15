package com.revolution.robotics.views.dialogs

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DialogButton(
    @StringRes val text: Int,
    @DrawableRes val icon: Int,
    var isHighlighted: Boolean = false,
    val isEnabledOnStart: Boolean = true,
    val onClick: () -> Unit
) {
    var viewModel: DialogButtonViewModel? = null
}
