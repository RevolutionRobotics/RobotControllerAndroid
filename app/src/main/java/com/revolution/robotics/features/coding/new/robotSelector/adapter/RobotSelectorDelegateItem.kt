package com.revolution.robotics.features.coding.programs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemProgramsBinding
import com.revolution.robotics.databinding.ItemRobotSelectorBinding

class RobotSelectorDelegateItem : AdapterDelegateItem<RobotViewModel>() {

    override fun canHandleData(data: Any) = data is RobotViewModel

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: RobotViewModel) {
        (viewHolder as ViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder =
        ViewHolder(ItemRobotSelectorBinding.inflate(inflater, parent, false))

    private class ViewHolder(val binding: ItemRobotSelectorBinding) : RecyclerView.ViewHolder(binding.root)
}
