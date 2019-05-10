package com.revolution.robotics.features.challenges.challengeList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemChallengeListChallengeBinding

class ChallengeDelegateItem : AdapterDelegateItem<ChallengeItem>() {

    override fun canHandleData(data: Any) = data is ChallengeItem

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: ChallengeItem) {
        (viewHolder as ViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val binding = ItemChallengeListChallengeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    private class ViewHolder(val binding: ItemChallengeListChallengeBinding) : RecyclerView.ViewHolder(binding.root)
}
