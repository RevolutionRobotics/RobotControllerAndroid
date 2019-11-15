package com.revolution.robotics.blockly.dialogs.variableOptions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemBlocklyVariableOptionsBinding

class VariableDelegateItem : AdapterDelegateItem<VariableViewModel>() {

    override fun canHandleData(data: Any) = data is VariableViewModel

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: VariableViewModel) {
        (viewHolder as ViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater) =
        ViewHolder(ItemBlocklyVariableOptionsBinding.inflate(inflater, parent, false))

    class ViewHolder(val binding: ItemBlocklyVariableOptionsBinding) : RecyclerView.ViewHolder(binding.root)
}
