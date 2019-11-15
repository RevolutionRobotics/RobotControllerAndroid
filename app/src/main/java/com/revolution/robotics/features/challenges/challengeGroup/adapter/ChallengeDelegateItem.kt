package com.revolution.robotics.features.challenges.challengeGroup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemChallengeListChallengeBinding

class ChallengeDelegateItem : AdapterDelegateItem<ChallengeGroupItem>() {

    override fun canHandleData(data: Any) = data is ChallengeGroupItem

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: ChallengeGroupItem) {
        (viewHolder as ViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val binding = ItemChallengeListChallengeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    private class ViewHolder(val binding: ItemChallengeListChallengeBinding) : RecyclerView.ViewHolder(binding.root)
}
