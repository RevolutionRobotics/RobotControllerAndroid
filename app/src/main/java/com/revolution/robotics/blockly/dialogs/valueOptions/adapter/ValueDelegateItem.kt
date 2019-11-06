package com.revolution.robotics.blockly.dialogs.valueOptions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemBlocklyValueOptionsBinding

class ValueDelegateItem : AdapterDelegateItem<ValueViewModel>() {

    override fun canHandleData(data: Any) = data is ValueViewModel

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: ValueViewModel) {
        (viewHolder as ViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater) =
        ViewHolder(ItemBlocklyValueOptionsBinding.inflate(inflater, parent, false))

    class ViewHolder(val binding: ItemBlocklyValueOptionsBinding) : RecyclerView.ViewHolder(binding.root)
}
