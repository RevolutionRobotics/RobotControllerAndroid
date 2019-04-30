package com.revolution.robotics.features.myRobots.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.databinding.ItemMyRobotsBinding
import com.revolution.robotics.views.carousel.CarouselAdapter

class MyRobotsCarouselAdapter : CarouselAdapter<MyRobotsItem>() {
    override fun bindItem(inflater: LayoutInflater, parent: ViewGroup, data: MyRobotsItem): ViewDataBinding {
        val binding = ItemMyRobotsBinding.inflate(inflater, parent, false)
        binding.viewModel = data
        return binding
    }
}
