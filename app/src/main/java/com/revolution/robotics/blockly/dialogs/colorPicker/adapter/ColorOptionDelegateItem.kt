package com.revolution.robotics.blockly.dialogs.colorPicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.blockly.dialogs.colorPicker.ColorOption
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemColorPickerBinding

class ColorOptionDelegateItem : AdapterDelegateItem<ColorOption>() {

    override fun canHandleData(data: Any) = data is ColorOption

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: ColorOption) {
        (viewHolder as ViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder =
        ViewHolder(
            ItemColorPickerBinding.inflate(inflater, parent, false)
        )

    private class ViewHolder(val binding: ItemColorPickerBinding) : RecyclerView.ViewHolder(binding.root)
}
