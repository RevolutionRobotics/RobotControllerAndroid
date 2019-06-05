package com.revolution.robotics.blockly.dialogs.optionSelector

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Option(
    @DrawableRes val drawableRes: Int,
    val text: String,
    val value: String,
    val isSelected: Boolean
) : Parcelable
