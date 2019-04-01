package com.revolution.robotics.core.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class AdapterDelegate<T : Any> {

    private val adapterItems: ArrayList<AdapterDelegateItem<T>> = ArrayList()

    fun register(vararg delegateItems: AdapterDelegateItem<T>) = delegateItems.forEach { item ->
        adapterItems.add(item)
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = getAdapterItemForViewType(viewType).onCreateViewHolder(parent)

    fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: T) = getAdapterItemForViewType(viewHolder.itemViewType).onBindViewHolder(viewHolder, data)

    fun getItemViewType(data: T): Int {
        adapterItems.forEach {
            if (it.canHandleData(data)) {
                return adapterItems.indexOf(it)
            }
        }
        throw IllegalArgumentException("Unhandled item: $data")
    }

    private fun getAdapterItemForViewType(type: Int): AdapterDelegateItem<T> = adapterItems[type]
}