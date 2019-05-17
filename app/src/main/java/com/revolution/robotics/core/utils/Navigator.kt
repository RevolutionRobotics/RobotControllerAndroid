package com.revolution.robotics.core.utils

import androidx.navigation.NavDirections

class Navigator {

    private var listener: NavigationEventListener? = null

    fun registerListener(listener: NavigationEventListener) {
        this.listener = listener
    }

    fun unregisterListener(listener: NavigationEventListener) {
        if (this.listener == listener) {
            this.listener = null
        }
    }

    fun navigate(navDirections: NavDirections) {
        listener?.onNavigationEvent(navDirections)
    }

    fun back(count: Int = 1) {
        listener?.back(count)
    }

    fun popUntil(fragmentId: Int) {
        listener?.popUntil(fragmentId)
    }

    interface NavigationEventListener {
        fun onNavigationEvent(navDirections: NavDirections)
        fun back(count: Int)
        fun popUntil(fragmentId: Int)
    }
}
