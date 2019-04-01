package com.revolution.robotics.core.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.revolution.robotics.core.glide.GlideApp

@BindingAdapter("android:src")
fun loadImage(imageView: ImageView, url: String?) {
    GlideApp.with(imageView)
        .load(url)
        .into(imageView)
}