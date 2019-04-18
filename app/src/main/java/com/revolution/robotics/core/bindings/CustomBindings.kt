package com.revolution.robotics.core.bindings

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.chippedBox.ChippedBoxDrawable
import com.revolution.robotics.features.whoToBuild.adapter.RobotItem
import com.revolution.robotics.features.whoToBuild.adapter.RobotsPagerAdapter
import com.revolution.robotics.features.availableRobots.adapter.AvailableRobotsAdapter
import com.revolution.robotics.features.availableRobots.adapter.AvailableRobotsItem

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

@BindingAdapter("robots")
fun setRobotsViewPagerItems(viewPager: ViewPager, itemList: List<RobotItem>?) {
    if (itemList != null && itemList.isNotEmpty()) {
        (viewPager.adapter as? RobotsPagerAdapter)?.setItems(itemList)
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
fun setAvailableRobotsItems(recyclerView: RecyclerView, itemList: List<AvailableRobotsItem>?) {
    if (itemList != null) {
        (recyclerView.adapter as? AvailableRobotsAdapter)?.setItems(itemList)
    }
}
