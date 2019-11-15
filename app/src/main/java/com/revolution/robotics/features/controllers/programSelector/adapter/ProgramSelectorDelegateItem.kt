package com.revolution.robotics.features.controllers.programSelector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemProgramSelectorBinding

class ProgramSelectorDelegateItem : AdapterDelegateItem<ProgramViewModel>() {

    override fun canHandleData(data: Any) = data is ProgramViewModel

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: ProgramViewModel) {
        (viewHolder as ViewHolder).binding.apply {
            viewModel = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val binding = ItemProgramSelectorBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemProgramSelectorBinding) : RecyclerView.ViewHolder(binding.root)
}
