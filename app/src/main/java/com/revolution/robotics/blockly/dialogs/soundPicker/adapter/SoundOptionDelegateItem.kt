package com.revolution.robotics.blockly.dialogs.soundPicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.blockly.dialogs.soundPicker.SoundOption
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemSoundPickerBinding

class SoundOptionDelegateItem : AdapterDelegateItem<SoundOption>() {

    override fun canHandleData(data: Any) = data is SoundOption

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: SoundOption) {
        (viewHolder as ViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder =
        ViewHolder(ItemSoundPickerBinding.inflate(inflater, parent, false))

    private class ViewHolder(val binding: ItemSoundPickerBinding) : RecyclerView.ViewHolder(binding.root)
}
