package com.revolution.robotics.core.utils.recyclerview

interface MemoryOptimizedViewHolder {

    enum class Reason {
        RECYCLED, DETACHED
    }

    fun initResources()

    fun clearResources(reason: Reason)
}
