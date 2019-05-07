package com.revolution.robotics.core.bindings

import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.chippedBox.ChippedBoxDrawable
import com.revolution.robotics.views.carousel.CarouselAdapter
import com.revolution.robotics.features.build.connect.adapter.ConnectAdapter
import com.revolution.robotics.features.build.connect.adapter.ConnectRobotItem

@BindingAdapter("listener")
fun setSeekbarListener(seekBar: SeekBar, listener: SeekBar.OnSeekBarChangeListener) {
    seekBar.setOnSeekBarChangeListener(listener)
}

@BindingAdapter("chippedBoxConfig")
fun setChippedBoxConfig(view: View, config: ChippedBoxConfig?) {
    if (config != null) {
        view.background = ChippedBoxDrawable(view.context, config)
    }
}

@BindingAdapter("drawable")
fun setImageDrawable(imageView: ImageView, @DrawableRes drawableRes: Int) {
    imageView.setImageResource(drawableRes)
}

@BindingAdapter("carouselItems")
fun setRobotsViewPagerItems(viewPager: ViewPager, itemList: List<Any>?) {
    if (itemList != null && itemList.isNotEmpty()) {
        (viewPager.adapter as? CarouselAdapter<Any>)?.setItems(itemList)
        viewPager.currentItem = 0
    }
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

@BindingAdapter("availableRobots")
fun setAvailableRobotsItems(recyclerView: RecyclerView, itemList: List<ConnectRobotItem>?) {
    if (itemList != null) {
        (recyclerView.adapter as? ConnectAdapter)?.setItems(itemList)
    }
}

@BindingAdapter("image")
fun setImageBitmap(imageView: ImageView, image: Bitmap?) {
    image?.let { imageView.setImageBitmap(it) }
}

@BindingAdapter("configButtonSelected")
fun setConfigButtonBackground(button: ImageView, configButtonSelected: Boolean) {
    if (configButtonSelected) {
        button.setColorFilter(button.context.color(R.color.grey_28), android.graphics.PorterDuff.Mode.SRC_IN)
        button.setBackgroundResource(R.drawable.bg_control_button_selected)
    } else {
        setChippedBoxConfig(
            button, ChippedBoxConfig.Builder()
                .backgroundColorResource(R.color.grey_28)
                .chipBottomLeft(true)
                .chipTopRight(true)
                .borderColorResource(R.color.grey_6d)
                .borderSize(R.dimen.dimen_1dp)
                .chipSize(R.dimen.dimen_8dp)
                .customDashed(R.dimen.dimen_6dp, R.dimen.dimen_6dp)
                .create()
        )
        button.setColorFilter(button.context.color(R.color.white), android.graphics.PorterDuff.Mode.SRC_IN)
    }
}
