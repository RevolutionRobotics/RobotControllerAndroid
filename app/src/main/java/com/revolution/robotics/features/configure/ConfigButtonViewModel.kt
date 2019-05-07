package com.revolution.robotics.features.configure

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean

data class ConfigButtonViewModel(@StringRes val name: Int, @DrawableRes val iconResource: Int) {
    val isSelected = ObservableBoolean(false)
    val isVisible = ObservableBoolean(true)
}