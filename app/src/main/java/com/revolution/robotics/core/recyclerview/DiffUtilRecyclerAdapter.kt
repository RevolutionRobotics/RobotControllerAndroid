package com.revolution.robotics.core.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.revolution.robotics.core.extensions.swap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class DiffUtilRecyclerAdapter<T : DiffUtilRecyclerAdapter.BaseListViewModel> : RecyclerAdapter<T>() {

    private var coroutine: CoroutineContext? = null

    override fun setItems(newItems: List<T>) {
        if (items.isEmpty()) {
            if (newItems.isNotEmpty()) {
                super.setItems(newItems)
            }
        } else {
            coroutine?.cancel()
            coroutine = GlobalScope.async(Dispatchers.Main) {
                val oldItems = items.toList()
                withContext(Dispatchers.IO) {
                    DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                        override fun getOldListSize() = oldItems.size

                        override fun getNewListSize() = newItems.size

                        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldItems[oldItemPosition].idField == newItems[newItemPosition].idField

                        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldItems[oldItemPosition] == newItems[newItemPosition]
                    })
                }.dispatchUpdatesTo(this@DiffUtilRecyclerAdapter)
                items.swap(newItems)
            }
        }
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