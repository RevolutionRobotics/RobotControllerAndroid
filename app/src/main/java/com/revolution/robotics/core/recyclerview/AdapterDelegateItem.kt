package com.revolution.robotics.core.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AdapterDelegateItem<in T> {

    abstract fun canHandleData(data: Any): Boolean

    abstract fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: T)

    abstract fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        onCreateViewHolder(parent, LayoutInflater.from(parent.context))
}
