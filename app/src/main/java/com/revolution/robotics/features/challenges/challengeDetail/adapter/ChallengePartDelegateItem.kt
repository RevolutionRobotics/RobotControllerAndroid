package com.revolution.robotics.features.challenges.challengeDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemChallengePartBinding

class ChallengePartDelegateItem : AdapterDelegateItem<ChallengePartItemViewModel>() {
    override fun canHandleData(data: Any) = data is ChallengePartItemViewModel

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: ChallengePartItemViewModel) {
        (viewHolder as ViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val binding = ItemChallengePartBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    private class ViewHolder(val binding: ItemChallengePartBinding) : RecyclerView.ViewHolder(binding.root)
}
