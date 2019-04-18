package com.revolution.robotics.features.availableRobots.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemAvailableRobotsBinding
import com.revolution.robotics.features.availableRobots.availableRobotsFace.AvailableRobotsDialogFaceViewModel

class AvailableRobotsAdapterDelegateItem(
    private val lifecycleOwner: LifecycleOwner
) : AdapterDelegateItem<AvailableRobotsItem>() {
    override fun canHandleData(data: Any): Boolean = data is AvailableRobotsItem

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: AvailableRobotsItem) {
        (viewHolder as AvailableRobotsViewHolder).binding.itemViewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val binding = ItemAvailableRobotsBinding.inflate(inflater, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return AvailableRobotsViewHolder(binding)
    }

    class AvailableRobotsViewHolder(val binding: ItemAvailableRobotsBinding) : RecyclerView.ViewHolder(binding.root)
}
