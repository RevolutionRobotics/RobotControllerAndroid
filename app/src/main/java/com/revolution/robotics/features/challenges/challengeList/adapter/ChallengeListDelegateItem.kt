package com.revolution.robotics.features.challenges.challengeList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemChallengeListBinding

class ChallengeListDelegateItem : AdapterDelegateItem<ChallengeListItem>() {

    override fun canHandleData(data: Any) = data is ChallengeListItem

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: ChallengeListItem) {
        (viewHolder as ViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val binding = ItemChallengeListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    private class ViewHolder(val binding: ItemChallengeListBinding) : RecyclerView.ViewHolder(binding.root)
}
