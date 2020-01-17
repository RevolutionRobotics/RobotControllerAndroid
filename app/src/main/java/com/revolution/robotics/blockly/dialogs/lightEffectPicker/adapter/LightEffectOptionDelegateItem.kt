package com.revolution.robotics.blockly.dialogs.lightEffectPicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.blockly.dialogs.lightEffectPicker.LightEffectOption
import com.revolution.robotics.blockly.dialogs.soundPicker.SoundOption
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemLightEffectPickerBinding
import com.revolution.robotics.databinding.ItemSoundPickerBinding

class LightEffectOptionDelegateItem : AdapterDelegateItem<LightEffectOption>() {

    override fun canHandleData(data: Any) = data is LightEffectOption

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: LightEffectOption) {
        (viewHolder as ViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder =
        ViewHolder(ItemLightEffectPickerBinding.inflate(inflater, parent, false))

    private class ViewHolder(val binding: ItemLightEffectPickerBinding) : RecyclerView.ViewHolder(binding.root)
}
