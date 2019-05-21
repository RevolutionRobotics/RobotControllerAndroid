package com.revolution.robotics.features.controllers.buttonless.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemButtonlessProgramBinding

class ButtonlessProgramDelegateItem : AdapterDelegateItem<ButtonlessProgramViewModel>() {
    override fun canHandleData(data: Any) = data is ButtonlessProgramViewModel

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: ButtonlessProgramViewModel) {
        (viewHolder as ViewHolder).binding.apply {
            viewModel = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val binding = ItemButtonlessProgramBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemButtonlessProgramBinding) : RecyclerView.ViewHolder(binding.root)
}
