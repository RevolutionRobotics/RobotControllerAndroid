package com.revolution.robotics.features.whoToBuild.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.databinding.ItemRobotsBinding
import com.revolution.robotics.views.carousel.CarouselAdapter

class RobotsCarouselAdapter : CarouselAdapter<RobotsItem>() {
    override fun bindItem(inflater: LayoutInflater, parent: ViewGroup, data: RobotsItem): ViewDataBinding {
        val binding = ItemRobotsBinding.inflate(inflater, parent, false)
        binding.viewModel = data
        return binding
    }
}
