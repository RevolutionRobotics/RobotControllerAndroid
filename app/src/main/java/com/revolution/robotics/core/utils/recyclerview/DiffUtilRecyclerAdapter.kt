package com.revolution.robotics.core.utils.recyclerview

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

abstract class DiffUtilRecyclerAdapter<T : DiffUtilRecyclerAdapter.BaseListViewModel> : RecyclerAdapter<T>() {

    private val asyncListDiffer by lazy {
        AsyncListDiffer(
            AdapterListUpdateCallback(this),
            AsyncDifferConfig.Builder<T>(object : DiffUtil.ItemCallback<T>() {
                override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem.idField == newItem.idField

                override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
            }).build()
        )
    }

    override val items: MutableList<T>
        get() {
            return asyncListDiffer.currentList
        }

    override fun setItems(newItems: List<T>) {
        asyncListDiffer.submitList(newItems)
    }

    abstract class BaseListViewModel {
        abstract val idField: Any

        override fun equals(other: Any?): Boolean {
            TODO("Write custom equals for the class")
        }

        override fun hashCode(): Int {
            TODO("Write custom hashcode for the class")
        }
    }
}
