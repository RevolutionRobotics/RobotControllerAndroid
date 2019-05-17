package com.revolution.robotics.core.utils.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revolution.robotics.core.extensions.swap
import java.util.Collections

abstract class RecyclerAdapter<T : Any> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected open val items = mutableListOf<T>()
    protected val adapterDelegate = AdapterDelegate<T>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        adapterDelegate.onBindViewHolder(holder, items[position])

    override fun getItemCount(): Int = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        adapterDelegate.onCreateViewHolder(parent, viewType)

    override fun getItemViewType(position: Int): Int = adapterDelegate.getItemViewType(items[position])

    open fun setItems(newItems: List<T>) {
        items.swap(newItems)
        notifyDataSetChanged()
    }

    fun swapItems(from: Int, to: Int) {
        if (from < to) {
            for (i in from until to) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in from downTo to + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
    }

    open fun getItem(position: Int): T = items[position]

    fun clearItems() = setItems(listOf())

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is MemoryOptimizedViewHolder) {
            holder.initResources()
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is MemoryOptimizedViewHolder) {
            holder.clearResources(MemoryOptimizedViewHolder.Reason.RECYCLED)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder is MemoryOptimizedViewHolder) {
            holder.clearResources(MemoryOptimizedViewHolder.Reason.DETACHED)
        }
    }
}
