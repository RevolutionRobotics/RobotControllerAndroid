package com.revolution.robotics.features.configure.controllers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.revolution.robotics.databinding.ItemControllersBinding
import com.revolution.robotics.databinding.ItemControllersNewBinding
import com.revolution.robotics.views.carousel.CarouselAdapter

class ControllersCarouselAdapter : CarouselAdapter<ControllersItem>() {
    override fun bindItem(
        inflater: LayoutInflater,
        parent: ViewGroup,
        data: ControllersItem,
        position: Int
    ): ViewDataBinding {
        return if (data is ControllersAddItem) {
            val binding = ItemControllersNewBinding.inflate(inflater, parent, false)
            binding.viewModel = data
            data.isSelected.set(position == selectedPosition)
            binding
        } else {
            val binding = ItemControllersBinding.inflate(inflater, parent, false)
            binding.viewModel = data
            data.isSelected.set(position == selectedPosition)
            binding
        }
    }
}
