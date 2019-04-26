package com.revolution.robotics.features.build.connect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.utils.recyclerview.AdapterDelegateItem
import com.revolution.robotics.databinding.ItemConnectRobotBinding

class ConnectRobotDelegateItem(private val lifecycleOwner: LifecycleOwner) : AdapterDelegateItem<ConnectRobotItem>() {

    override fun canHandleData(data: Any): Boolean = data is ConnectRobotItem

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: ConnectRobotItem) {
        (viewHolder as ConnectViewHolder).binding.viewModel = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val binding = ItemConnectRobotBinding.inflate(inflater, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return ConnectViewHolder(binding)
    }

    class ConnectViewHolder(val binding: ItemConnectRobotBinding) : RecyclerView.ViewHolder(binding.root)
}
