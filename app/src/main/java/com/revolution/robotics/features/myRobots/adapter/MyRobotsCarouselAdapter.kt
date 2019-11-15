package com.revolution.robotics.features.myRobots.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.databinding.ItemMyRobotsBinding
import com.revolution.robotics.databinding.ItemMyRobotsNewBinding
import com.revolution.robotics.views.carousel.CarouselAdapter

class MyRobotsCarouselAdapter : CarouselAdapter<MyRobotsItem>() {
    override fun bindItem(
        inflater: LayoutInflater,
        parent: ViewGroup,
        data: MyRobotsItem,
        position: Int
    ): ViewDataBinding {
        return if (data is MyRobotsAddItem) {
            val binding = ItemMyRobotsNewBinding.inflate(inflater, parent, false)
            binding.viewModel = data
            data.isSelected.set(position == selectedPosition)
            binding
        } else {
            val binding = ItemMyRobotsBinding.inflate(inflater, parent, false)
            binding.viewModel = data
            data.isSelected.set(position == selectedPosition)
            binding
        }
    }
}
