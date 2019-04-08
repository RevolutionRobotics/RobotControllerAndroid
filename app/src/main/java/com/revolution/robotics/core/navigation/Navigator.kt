package com.revolution.robotics.core.navigation

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

    interface NavigationEventListener {
        fun onNavigationEvent(navDirections: NavDirections)
    }
}
