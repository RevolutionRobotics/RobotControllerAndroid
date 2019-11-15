package com.revolution.robotics.features.coding.programs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemProgramsBinding

class ProgramsDelegateItem : AdapterDelegateItem<ProgramViewModel>() {

    override fun canHandleData(data: Any) = data is ProgramViewModel

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: ProgramViewModel) {
        (viewHolder as ViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder =
        ViewHolder(ItemProgramsBinding.inflate(inflater, parent, false))

    private class ViewHolder(val binding: ItemProgramsBinding) : RecyclerView.ViewHolder(binding.root)
}
