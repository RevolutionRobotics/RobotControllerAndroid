package com.revolution.robotics.core.bindings

import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

@BindingAdapter("drawable")
fun setImageDrawable(imageView: ImageView, @DrawableRes drawableRes: Int) {
    imageView.setImageResource(drawableRes)
}

@BindingAdapter("greyscale")
fun setGreyscale(imageView: ImageView, greyscale: Boolean) {
    imageView.colorFilter =
        if (greyscale) {
            val matrix = ColorMatrix()
            matrix.setSaturation(0.0f)
            ColorMatrixColorFilter(matrix)
        } else {
            null
        }
}

@BindingAdapter("image")
fun setImageBitmap(imageView: ImageView, image: Bitmap?) {
    image?.let { imageView.setImageBitmap(it) }
}

@BindingAdapter("tintColorResource")
fun setTintColor(imageView: ImageView, @ColorRes color: Int) {
    if (color != 0) {
        imageView.setColorFilter(imageView.context.color(color), android.graphics.PorterDuff.Mode.SRC_IN)
    }
}

@BindingAdapter("configButtonSelected")
fun setConfigButtonBackground(imageView: ImageView, configButtonSelected: Boolean) {
    if (configButtonSelected) {
        imageView.setColorFilter(imageView.context.color(R.color.grey_28), android.graphics.PorterDuff.Mode.SRC_IN)
        imageView.setBackgroundResource(R.drawable.bg_control_button_selected)
    } else {
        setChippedBoxConfig(
            imageView, ChippedBoxConfig.Builder()
                .backgroundColorResource(R.color.grey_28)
                .chipBottomLeft(true)
                .chipTopRight(true)
                .borderColorResource(R.color.grey_6d)
                .borderSize(R.dimen.dimen_1dp)
                .chipSize(R.dimen.dimen_8dp)
                .customDashed(R.dimen.dimen_6dp, R.dimen.dimen_6dp)
                .create()
        )
        imageView.setColorFilter(imageView.context.color(R.color.white), android.graphics.PorterDuff.Mode.SRC_IN)
    }
}
