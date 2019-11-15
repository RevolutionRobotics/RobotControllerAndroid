package com.revolution.robotics.features.whoToBuild.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.databinding.ItemRobotsBinding
import com.revolution.robotics.databinding.ItemRobotsBuildYourOwnBinding
import com.revolution.robotics.views.carousel.CarouselAdapter

class RobotsCarouselAdapter : CarouselAdapter<RobotsItem>() {
    override fun bindItem(
        inflater: LayoutInflater,
        parent: ViewGroup,
        data: RobotsItem,
        position: Int
    ): ViewDataBinding {
        return if (data is RobotsBuildYourOwnItem) {
            val binding = ItemRobotsBuildYourOwnBinding.inflate(inflater, parent, false)
            binding.viewModel = data
            data.isSelected.set(position == selectedPosition)
            binding
        } else {
            val binding = ItemRobotsBinding.inflate(inflater, parent, false)
            binding.viewModel = data
            data.isSelected.set(position == selectedPosition)
            binding
        }
    }
}
