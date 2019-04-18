package com.revolution.robotics.core.bindings

import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxConfig
import com.revolution.robotics.core.utils.chippedBox.ChippedBoxDrawable
import com.revolution.robotics.robots.adapter.RobotsAdapterItem
import com.revolution.robotics.robots.adapter.RobotsPagerAdapter

@BindingAdapter("listener")
fun setSeekbarListener(seekBar: SeekBar, listener: SeekBar.OnSeekBarChangeListener) {
    seekBar.setOnSeekBarChangeListener(listener)
}

@BindingAdapter("chippedBoxConfig")
fun setChippedBoxConfig(view: View, config: ChippedBoxConfig) {
    view.background = ChippedBoxDrawable(view.context, config)
}

@BindingAdapter("drawable")
fun setImageDrawable(imageView: ImageView, @DrawableRes drawableRes: Int) {
    imageView.setImageResource(drawableRes)
}

@BindingAdapter("robots", "startIndex")
fun setRobotsViewPagerItems(viewPager: ViewPager, itemList: List<RobotsAdapterItem>, startIndex: Int) {
    (viewPager.adapter as? RobotsPagerAdapter)?.setItems(itemList)
    viewPager.currentItem = startIndex
}
