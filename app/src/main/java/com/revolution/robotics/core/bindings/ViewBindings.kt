package com.revolution.robotics.core.bindings

import android.view.View
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("backgroundResource")
fun setBacktoundResource(view: View, @DrawableRes background: Int) {
    view.setBackgroundResource(background)
}